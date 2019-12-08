package com.stimednp.kadesubmission4.ui.main

import com.stimednp.kadesubmission4.base.IView
import com.stimednp.kadesubmission4.model.ResponseLeagues
import com.stimednp.kadesubmission4.presenter.main.IMainRepositoryCallback

/**
 * Created by rivaldy on 12/8/2019.
 */

interface IMainView: IMainRepositoryCallback<ResponseLeagues>, IView {
    fun showMessage(text: String)
    fun showTextEmpty(text: String)
}