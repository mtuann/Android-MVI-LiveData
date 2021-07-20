/*
 * Copyright 2021 UET-AILAB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.modelviewintent.default_act_mvi

import aacmvi.AacMviViewModel
import android.app.Application

class DefaultVM(application: Application) : AacMviViewModel<DefaultState, DefaultEffect, DefaultEvent>(application) {
    companion object {
        const val TAG = "DefaultVM"
    }
    init {
        viewState = DefaultState(
            status = DefaultStatus.Start,
            message = "Start-Default-Activity"
        )
    }



    fun reduce(reducer: DefaultReducer) {
        val result = reducer.reduce()
        reduce(result)
    }


    fun reduce(result: DefaultObject) {
        result.viewState?.let { viewState = it }
        result.viewEffect?.let { viewEffect = it }
    }
}