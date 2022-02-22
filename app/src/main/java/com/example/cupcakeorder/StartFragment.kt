package com.example.cupcakeorder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcakeorder.databinding.FragmentStartBinding
import com.example.cupcakeorder.model.OrderViewModel


class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null

    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate( inflater, container, false)
        binding = fragmentBinding
        // Inflate the layout for this fragment
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.startFragment = this

        binding?.apply {  }

        // Apply => Funcion de alcance => aplica a las sig asignaciones al objeto
        /* binding?.apply { orderOneCupcake.setOnClickListener { orderCupcake( 1) } }*/
    }

    fun orderCupcake(quantity: Int) {
        Toast.makeText( activity, "Ordered $quantity cupcake(s)", Toast.LENGTH_SHORT).show()

        sharedViewModel.setQuantity( quantity)
        if (sharedViewModel.hasNoFlavorSet()) {
            sharedViewModel.setFlavor( getString(R.string.vanilla))
        }

        findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}