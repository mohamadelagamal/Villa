package com.ui.room.spinner

import com.ui.R

data class Category(
    var id :String?=null,
    var name :String?=null,
    var imageId : Int?=null
){
    companion object{
        const val House = "House"
        const val Villa = "Villa"
        const val BeachHouse = "Movies"
        fun FromID(category:String):Category{
            when(category){
                House->{
                    return Category(House, name = "House", imageId = R.drawable.house_icon_spinner)
                }
                Villa->{
                    return Category(Villa, name = "Villa", imageId = R.drawable.villa_icon_spinner)
                }
                BeachHouse->{
                    return Category(BeachHouse, name = "BeachHouse", imageId = R.drawable.beach_icon_spinner)
                }
                else->{
                    return Category(House, name = "House", imageId = R.drawable.house_icon_spinner)
                }

            }
        }
        fun getCategoriesList(): List<Category>{
            return listOf(
                FromID(House),
                FromID(Villa),
                FromID(BeachHouse)
            )
        }
        fun itemRoomShow(cat:String):Category{
            when(cat){
                Villa->{
                    return Category(Villa, name = "Villa", imageId = R.drawable.villa)
                }
                BeachHouse->{
                    return Category(BeachHouse, name = "BeachHouse", imageId = R.drawable.beach_house)
                }
                House->{
                    return Category(House, name = "House", imageId = R.drawable.houses)
                }
                else->{
                    return Category(Villa, name = "Villa", imageId = R.drawable.villa)
                }
            }
        }

    }
}
