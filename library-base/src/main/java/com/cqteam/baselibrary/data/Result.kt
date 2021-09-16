package com.cqteam.baselibrary.data

import java.lang.Exception

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 11:16 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
sealed class Result<out R> {

    data class Success<T>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()

}