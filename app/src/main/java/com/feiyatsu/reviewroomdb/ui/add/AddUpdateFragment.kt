package com.feiyatsu.reviewroomdb.ui.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.feiyatsu.reviewroomdb.R
import com.feiyatsu.reviewroomdb.data.User
import com.feiyatsu.reviewroomdb.databinding.FragmentAddBinding
import com.feiyatsu.reviewroomdb.ui.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUpdateFragment : Fragment(R.layout.fragment_add) {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    private val args by navArgs<AddUpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        setUpUserInfo()
        setUpAddButton()
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
            }
        }
    }

    private fun setUpAddButton() {
        binding.buttonAdd.setOnClickListener {
            insertUserToDatabase()
        }
    }

    private fun insertUserToDatabase() {
        val firstName = binding.editTextFirstName.text.toString()
        val lastName = binding.editTextLastName.text.toString()
        val age = binding.editTextAge.text

        if (inputCheck(firstName, lastName, age)) {
            // Update mode
            if(args.currentUser != null){
                val user = User(args.currentUser!!.id, firstName, lastName, Integer.parseInt(age.toString()))
                userViewModel.updateUser(user)
                Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            }
            // Add new mode
            else{
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
}