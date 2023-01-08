package com.lafa.common

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

class Validation
{

    fun checkEmail(context: Context, email: String): String
    {
        if (email.isEmpty())
        {
            Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
        }

        return email
    }

    fun checkName(context: Context, name: String): String
    {

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
        }

        return name
    }


    fun checkPhone(context: Context, phone: String): String
    {

        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(context, "Please enter your phone", Toast.LENGTH_SHORT).show()
        }

        return phone
    }

    fun checkPassword(context: Context, password: String): String
    {

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
        }

        return password
    }

    fun checkSearchProduct(context: Context, searchProduct: String): String
    {

        if (TextUtils.isEmpty(searchProduct))
        {
            Toast.makeText(context, "Please enter search product", Toast.LENGTH_SHORT).show()
        }

        return searchProduct
    }

    fun checkMessage(context: Context, message: String): String
    {

        if (TextUtils.isEmpty(message))
        {
            Toast.makeText(context, "Please enter your message", Toast.LENGTH_SHORT).show()
        }

        return message
    }
}