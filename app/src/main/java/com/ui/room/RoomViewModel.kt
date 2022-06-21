package com.ui.room
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.base.BaseViewModel

class RoomViewModel: BaseViewModel<Navigator>() {

    var counterRoom :Int =0
    var counterBath :Int =0
    var counterParking :Int =0
    var numberRoom =ObservableField<String>()
    var numberBath =ObservableField<String>()
    var numberParking =ObservableField<String>()
    var roomName = ObservableField<String>()
    var priceRoom = ObservableField<String>()
    var roomNameError = ObservableField<String>()
    var priceError = ObservableField<String>()
    var description = ObservableField<String>()
    var descriptionError = ObservableField<String>()
    var houseItemSpinner :Boolean = false
    var beachHouseItemSpinner :Boolean = false
    var villaItemSpinner :Boolean = false
    //.. chose buying or rent
    //... set house Item form spinner
    // to now this items added successfully
    fun createRoom(){
       if (houseItemSpinner==true && beachHouseItemSpinner==false && villaItemSpinner==false ){
           if (VaildAtion()){
                        navigator?.houseDatabase()
                    //messageLiveData.value = houseItemSpinner.toString()
                    }
        }
        if (villaItemSpinner==true && beachHouseItemSpinner==false && houseItemSpinner==false){
            if (VaildAtion()){
                navigator?.villaDatabase()
            }
        }
        if (houseItemSpinner==false && beachHouseItemSpinner==true && villaItemSpinner==false ){
            if (VaildAtion()){
                navigator?.beachHouseDatabase()
                //messageLiveData.value = houseItemSpinner.toString()
            }
        }
    }

    fun backHome(){
        navigator?.backHome()
    }
    fun VaildAtion(): Boolean{
        var valid = true
        if (roomName.get().isNullOrBlank()){
            roomNameError.set("Please enter Room Name")
            valid=false
        }
        else{
            roomNameError.set(null)
        }
        if (description.get().isNullOrBlank()){
            descriptionError.set("Please enter Room Description")
            valid=false
        }
        else{
            descriptionError.set(null)
        }
        if (priceRoom.get().isNullOrBlank()){
            priceError.set("Please enter Room Price")
            valid=false
        }
        else{
            priceError.set(null)
        }
        return valid
    }
     fun addRoom(){
         counterRoom ++
         numberRoom.set(counterRoom.toString())
     }
    fun removeRoom(){
        if (counterRoom>0){
            counterRoom --
            numberRoom.set(counterRoom.toString())
        }

    }
    fun addBath(){
        counterBath ++
        numberBath.set(counterBath.toString())
    }
    fun removeBath(){
        if (counterBath>0){
        counterBath --
        numberBath.set(counterBath.toString())
    }}
    fun addParking(){
        counterParking ++
        numberParking.set(counterParking.toString())
    }
    fun removeParking(){
        if (counterParking>0){
        counterParking --
        numberParking.set(counterParking.toString())
    }}

    fun showStorage(){
        navigator?.showCamera()
    }

}