package com.rogo.final_project.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rogo.final_project.local.datastore.TokenDataStore
import com.rogo.final_project.network.RestfulApi
import com.rogo.final_project.view.model.data.flight.GetFlightResponse
import com.rogo.final_project.view.model.data.search.Data
import com.rogo.final_project.view.model.data.search.SearchTiketsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import javax.inject.Inject
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: RestfulApi,
    private val dataStore: TokenDataStore,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {


    private val _flight = MutableLiveData<GetFlightResponse>()
    val flight: MutableLiveData<GetFlightResponse> = _flight

    fun getFlights() {
        api.getFlights().enqueue(object: Callback<GetFlightResponse>{
            override fun onResponse(
                call: Call<GetFlightResponse>,
                response: Response<GetFlightResponse>
            ) {
                if (response.isSuccessful){
                    _flight.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<GetFlightResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    fun savePenumpangPreferences(dewasa: Int,anak: Int,bayi: Int){
        val editor = sharedPreferences.edit()
        editor.putInt("dewasa",dewasa)
        editor.putInt("anak",anak)
        editor.putInt("bayi",bayi)
        editor.apply()
    }

    fun getPenumpangDewasa():Int{
        return sharedPreferences.getInt("dewasa",0)
    }

    fun getPenumpangAnak():Int{
        return sharedPreferences.getInt("anak",0)
    }

    fun getPenumpangBayi():Int{
        return sharedPreferences.getInt("bayi",0)
    }


    fun saveDatePref(date:String){
        val editor = sharedPreferences.edit()
        editor.putString("date", date)
        editor.apply()
    }

    fun getArrivedDate(): String? {
        val nameMonth = ArrayList<String>()
        nameMonth.add("Januari")
        nameMonth.add("Februari")
        nameMonth.add("Maret")
        nameMonth.add("April")
        nameMonth.add("Mei")
        nameMonth.add("Juni")
        nameMonth.add("Juli")
        nameMonth.add("Agustus")
        nameMonth.add("September")
        nameMonth.add("Oktober")
        nameMonth.add("November")
        nameMonth.add("Desember")

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val bulan = nameMonth[month]
//        val tanggal = "2023-5-27"
        val defaultTanggal = "$year-${month+1}-$day"
        return sharedPreferences.getString("date",defaultTanggal)
    }

    fun getDepartureDate():String?{
        val nameMonth = ArrayList<String>()
        nameMonth.add("Januari")
        nameMonth.add("Februari")
        nameMonth.add("Maret")
        nameMonth.add("April")
        nameMonth.add("Mei")
        nameMonth.add("Juni")
        nameMonth.add("Juli")
        nameMonth.add("Agustus")
        nameMonth.add("September")
        nameMonth.add("Oktober")
        nameMonth.add("November")
        nameMonth.add("Desember")

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val bulan = nameMonth[month]
//        val tanggal = "2023-5-26"
        val defaultTanggal = "$year-0${month+1}-0$day"
        return sharedPreferences.getString("departure",defaultTanggal)
    }

    fun saveselected(isSelected: Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean("selected", isSelected)
    }

    fun getCheckedSwitch() : Boolean{
        return  sharedPreferences.getBoolean("selected", true)
    }

    fun saveDepartureDate(date: String){
        val editor = sharedPreferences.edit()
        editor.putString("departure",date)
        editor.apply()
    }

    fun getCityFrom():String?{
        return sharedPreferences.getString("keyFrom"," ")
    }

    fun getIdDep():Int?{
        return sharedPreferences.getInt("idDep",0)
    }

    fun getIdReturn(): Int?{
        return sharedPreferences.getInt("idReturn",0)
    }

    fun getTicketId(): Int?{
        return sharedPreferences.getInt("TicketId", 0)
    }

    fun getCityTo():String?{
        return sharedPreferences.getString("keyTo"," ")
    }

    fun getorder():String?{
        return sharedPreferences.getString("order","price")
    }


    fun saveIdDeparture(idDep:Int){
        val editor = sharedPreferences.edit()
        editor.putInt("idDep",idDep)
        editor.apply()
    }

    fun saveIdReturn(idReturn:Int){
        val editor = sharedPreferences.edit()
        editor.putInt("idReturn",idReturn)
        editor.apply()
    }

    fun saveIdTicket(ticketId:Int){
        val editor =  sharedPreferences.edit()
        editor.putInt("TicketId",ticketId)
        editor.apply()
    }

    private val _searching = MutableLiveData<SearchTiketsResponse>()
    val searching: LiveData<SearchTiketsResponse> = _searching

    fun callGetSearchAirport(departureCity: String) {
        api.getdestinationsfrom(departureCity).enqueue(object : Callback<SearchTiketsResponse> {
            override fun onResponse(
                call: Call<SearchTiketsResponse>,
                response: Response<SearchTiketsResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _searching.postValue(response.body())
                    }
                } else {
                    Log.e("Error : ", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchTiketsResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure : ${t.message}")
            }

        })
    }

    fun saveCityFrom(departureCity:String){
        val editor = sharedPreferences.edit()
        editor.putString("keyFrom",departureCity)
        editor.apply()
    }

    private val _searchingTo = MutableLiveData<SearchTiketsResponse>()
    val searchingTo: LiveData<SearchTiketsResponse> = _searchingTo

    fun callGetSearchTo(arrivalCity: String) {
        api.getdestinationsto(arrivalCity).enqueue(object : Callback<SearchTiketsResponse> {
            override fun onResponse(
                call: Call<SearchTiketsResponse>,
                response: Response<SearchTiketsResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _searchingTo.postValue(response.body())
                    }
                } else {
                    Log.e("Error : ", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchTiketsResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure : ${t.message}")
            }

        })
    }

    fun saveCityTo(arrivalCity:String){
        val editor = sharedPreferences.edit()
        editor.putString("keyTo",arrivalCity)
        editor.apply()
    }

    val _searchallticket : MutableLiveData<List<Data>> = MutableLiveData()
    val livedatasearchallticket : LiveData<List<Data>> = _searchallticket

    fun searchallticket(departureCity:String,arrivalCity:String,departureDate:String,arrivedDate:String){
        api.getallticket(departureCity, arrivalCity, departureDate, arrivedDate).enqueue(object : Callback<SearchTiketsResponse>{
            override fun onResponse(
                call: Call<SearchTiketsResponse>,
                response: Response<SearchTiketsResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _searchallticket.value = response.body()!!.data
                    }
                } else {
                    Log.e("Error : ", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchTiketsResponse>, t: Throwable) {
                Log.e("HomeViewModel", "Cannot send data1")
            }

        })
    }
}