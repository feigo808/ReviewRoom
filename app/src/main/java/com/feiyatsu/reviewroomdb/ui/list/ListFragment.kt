package com.feiyatsu.reviewroomdb.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.feiyatsu.reviewroomdb.R
import com.feiyatsu.reviewroomdb.databinding.FragmentListBinding
import com.feiyatsu.reviewroomdb.ui.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()
    private val listAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        // Add menu delete
        setHasOptionsMenu(true)

        binding.floatingActionButton.setOnClickListener {
            // Without sending arguments we can just do this
            // findNavController().navigate(R.id.action_listFragment_to_addFragment)

            // With arguments, have to set the action
            val action = ListFragmentDirections.actionListFragmentToAddFragment(null)
            findNavController().navigate(action)
        }

        // RecyclerView
        binding.recyclerViewUserList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { userList ->
            listAdapter.setData(userList)
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllData() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to Delete all users?")
            .setTitle("Delete all users on the list ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected user from database
                userViewModel.deleteAllData()
                Toast.makeText(
                    requireContext(),
                    "Successfully deleted all users!",
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}