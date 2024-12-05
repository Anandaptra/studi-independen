package com.rogo.final_project.view.fragment.Payment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rogo.final_project.R
import com.rogo.final_project.databinding.FragmentPaymentBinding
import com.rogo.final_project.view.fragment.checkout.BottomsheetPayment
import com.rogo.final_project.viewmodel.DetailViewModel
import com.rogo.final_project.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {
 //contoh
    lateinit var binding : FragmentPaymentBinding
    private lateinit var DetailVm : DetailViewModel
    private val homeViewModel : HomeViewModel by viewModels()
    lateinit var sharedPref : SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        DetailVm = ViewModelProvider(this).get(DetailViewModel::class.java)

//        val idSingle = homeViewModel.getTicketId()
//        DetailVm.getdetailticket(idSingle!!)

        val idDeparture = homeViewModel.getIdDep()
        DetailVm.getdetailticket(idDeparture!!)

        DetailVm.livedetailticket.observe(viewLifecycleOwner) { detail ->
//            homeViewModel.saveIdTicket(id!!)
            binding.apply {
                val departureTime = detail!!.ticket.flight.departureTime
                val arrivalTime = detail.ticket.flight.arrivalTime
                val dateDepature = detail.ticket.flight.departureDate
                val arrivalDate = detail.ticket.flight.arrivalDate
                val depatureCity = detail.ticket.flight.departureCity
                val arrivalCity = detail.ticket.flight.arrivalCity
                val classSeat = detail.ticket.classSeat
                val hargatotal = detail.ticket.price

                val dewasa = homeViewModel.getPenumpangDewasa()
                val anak = homeViewModel.getPenumpangAnak()
                val bayi = homeViewModel.getPenumpangBayi()
                val total = dewasa + anak + bayi

                tvJam.text = arrivalTime
                tvJamDua.text = departureTime
                tvDeparture.text = depatureCity
                tvDepartureDua.text = arrivalCity
                tvTanggal.text = dateDepature
                tvTanggalDua.text = arrivalDate
                typeClass.text = classSeat
                tvHarga.text = hargatotal.toString()
                tvTypePassengersCount.text = "$total Passangers"
            }
        }

        binding.apply {
            cvGopay.setOnClickListener {
                if (expandLayoutGopay.visibility == View.GONE){
                    TransitionManager.beginDelayedTransition(cvGopay, AutoTransition())
                    expandLayoutGopay.visibility = View.VISIBLE
                    constraintLay.setBackgroundColor(resources.getColor(R.color.darkblue05))
                    ivArrow.setImageResource(R.drawable.atas)
                }else{
                    TransitionManager.beginDelayedTransition(cvGopay, AutoTransition())
                    expandLayoutGopay.visibility = View.GONE
                    ivArrow.setImageResource(R.drawable.bawah)
                    constraintLay.setBackgroundColor(resources.getColor(R.color.neutral_04))
                }
            }

            cvCreditCard.setOnClickListener {
                if (expandLayoutCreditCard.visibility ==View.GONE){
                    TransitionManager.beginDelayedTransition(cvCreditCard, AutoTransition())
                    expandLayoutCreditCard.visibility = View.VISIBLE
                    constraintLay2.setBackgroundColor(resources.getColor(R.color.darkblue05))
                    ivArrow2.setImageResource(R.drawable.atas)
                }else{
                    TransitionManager.beginDelayedTransition(cvCreditCard, AutoTransition())
                    expandLayoutCreditCard.visibility = View.GONE
                    constraintLay2.setBackgroundColor(resources.getColor(R.color.neutral_04))
                    ivArrow2.setImageResource(R.drawable.bawah)
                }
            }

            btnBayarGopay.setOnClickListener {
                BottomsheetPayment().show(requireActivity().supportFragmentManager,BottomsheetPayment.bottomTag)
            }
            btnBayarCreditCard.setOnClickListener {
                BottomsheetPayment().show(requireActivity().supportFragmentManager,BottomsheetPayment.bottomTag)
            }

            topAppBar.setNavigationOnClickListener {
                findNavController().navigate(R.id.checkoutFragment)
            }
        }
    }
}