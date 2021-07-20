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

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultMVIListener(val listener: ProgressDownloadListener) {

    interface ProgressDownloadListener {
        fun update(bytesRead: Long, contentLength: Long, done: Boolean)
    }

    companion object {
        const val TAG = "DefaultMVIListener"
    }

    private fun renderViewEffect(defaultActivity: DefaultActivity, viewEffect: DefaultEffect) {

    }

    private fun renderViewState(defaultActivity: DefaultActivity, viewState: DefaultState) {

    }

    fun process(defaultVM: DefaultVM, defaultEvent: DefaultEvent) {

        when (defaultEvent) {

        }
    }

    inner class Reducer(viewModel: DefaultVM, viewState: DefaultState, val viewEvent: DefaultEvent)
        : DefaultReducer(viewModel, viewState, viewEvent) {
        override fun reduce(): DefaultObject {
            when(viewEvent) {

            }
            return DefaultObject()
        }

    }

    inner class ReducerAsync(viewModel: DefaultVM, viewState: DefaultState, viewEvent: DefaultEvent) : DefaultReducer(viewModel, viewState, viewEvent) {

        override fun reduce(): DefaultObject {
            viewModel.viewModelScope.launch {
                val result = asyncRun()
                viewModel.viewStates().value?.let {
                    viewModel.reduce(DefaultObject())
                }
            }
            return DefaultObject()
        }

        suspend fun asyncRun() : Int = withContext(Dispatchers.IO) {
            repeat(100000000) {
                100*100
            }
            return@withContext 1234
        }
    }
}