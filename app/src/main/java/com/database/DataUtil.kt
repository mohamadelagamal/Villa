package com.database

import com.google.firebase.firestore.auth.User
import com.model.ApplicationUser

object DataUtil {
    var user :ApplicationUser?=null
    var firebaseUser:User?=null
    var languageArabic : Boolean ? = false
    var languageEnglish: Boolean ? = false

}