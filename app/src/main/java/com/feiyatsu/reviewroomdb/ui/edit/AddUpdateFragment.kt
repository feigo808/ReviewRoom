package com.feiyatsu.reviewroomdb.ui.edit

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.feiyatsu.reviewroomdb.R
import com.feiyatsu.reviewroomdb.data.User
import com.feiyatsu.reviewroomdb.databinding.FragmentAddUpdateBinding
import com.feiyatsu.reviewroomdb.ui.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUpdateFragment : Fragment(R.layout.fragment_add_update) {

    private var _binding: FragmentAddUpdateBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    private val args by navArgs<AddUpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddUpdateBinding.inflate(inflater, container, false)

        setUpUserInfo()
        setUpEditButton()
        return binding.root
    }

    private fun setUpUserInfo() {
        args.currentUser.let { userInfo ->
            if (userInfo != null) {
                binding.apply {
                    editTextFirstName.setText(userInfo.firstName)
                    editTextLastName.setText(userInfo.lastName)
                    editTextAge.setText(userInfo.age.toString())
                    buttonAdd.text = getString(R.string.update)
                }

                // Add menu delete
                setHasOptionsMenu(true)
            }
        }
    }

    private fun setUpEditButton() {
        binding.buttonAdd.setOnClickListener {
            upsertUserToDatabase()
        }
    }

    private fun upsertUserToDatabase() {
        val firstName = binding.editTextFirstName.text.toString()
        val lastName = binding.editTextLastName.text.toString()
        val age = binding.editTextAge.text

        if (inputCheck(firstName, lastName, age)) {
            // Update mode
            if (args.currentUser != null) {
                val user = User(
                    args.currentUser!!.id,
                    firstName,
                    lastName,
                    Integer.parseInt(age.toString())
                )
                userViewModel.updateUser(user)
                Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            }
            // Add new mode
            else {
                val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))
                userViewModel.addUser(user)
                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            }
            // Navigate Back to List Fragment
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all field", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName)
                || (TextUtils.isEmpty(lastName))
                || (TextUtils.isEmpty(age)))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to Delete this user?")
            .setTitle("Delete ${args.currentUser?.firstName} ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected user from database
                args.currentUser?.let { userViewModel.deleteUser(it) }
                // Navigate Back to List Fragment
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
                Toast.makeText(
                    requireContext(),
                    "Successfully deleted ${args.currentUser?.firstName}!",
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