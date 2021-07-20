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

package com.example.modelviewintent.default_act_mvi.fragment_00

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayBackMVI {

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: PlayBackMVI? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: PlayBackMVI()
                        .also { instance = it }
            }

        const val TAG = "PlayBackMVI"

        fun process(playBackVM: PlayBackVM, PlayBackEvent: PlayBackEvent) {
            getInstance().process(playBackVM, PlayBackEvent)
        }

        fun renderViewState(playBackFragment: PlayBackFragment, viewState: PlayBackState) {
            getInstance().renderViewState(playBackFragment, viewState)
        }

        fun renderViewEffect(playBackFragment: PlayBackFragment, viewEffect: PlayBackEffect) {
            getInstance().renderViewEffect(playBackFragment, viewEffect)
        }
    }

    private fun renderViewEffect(playBackFragment: PlayBackFragment, viewEffect: PlayBackEffect) {

    }

    private fun renderViewState(playBackFragment: PlayBackFragment, viewState: PlayBackState) {

    }

    fun process(playBackVM: PlayBackVM, playBackEvent: PlayBackEvent) {

        when (playBackEvent) {

        }
    }

    inner class Reducer(viewModel: PlayBackVM, viewState: PlayBackState, val viewEvent: PlayBackEvent)
        : PlayBackReducer(viewModel, viewState, viewEvent) {
        override fun reduce(): PlayBackObject {
            when(viewEvent) {

            }
            return PlayBackObject()
        }

    }

    inner class ReducerAsync(viewModel: PlayBackVM, viewState: PlayBackState, viewEvent: PlayBackEvent) : PlayBackReducer(viewModel, viewState, viewEvent) {

        override fun reduce(): PlayBackObject {
            viewModel.viewModelScope.launch {
                val result = asyncRun()
                viewModel.viewStates().value?.let {
                    viewModel.reduce(PlayBackObject())
                }
            }
            return PlayBackObject()
        }

        suspend fun asyncRun() : Int = withContext(Dispatchers.IO) {
            repeat(100000000) {
                100*100
            }
            return@withContext 1234
        }
    }
}
