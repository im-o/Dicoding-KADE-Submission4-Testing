package com.stimednp.kadesubmission4.ui.detailleagues

import com.stimednp.kadesubmission4.base.IView
import com.stimednp.kadesubmission4.model.ResponseLeagues
import com.stimednp.kadesubmission4.presenter.detailleagues.IDetailsLRepositoryCallback

/**
 * Created by rivaldy on 12/8/2019.
 */

interface IDetailsLView: IDetailsLRepositoryCallback<ResponseLeagues>, IView {
    fun showMessage(text: String)
}