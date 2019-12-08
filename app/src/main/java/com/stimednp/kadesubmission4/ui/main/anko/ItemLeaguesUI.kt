package com.stimednp.kadesubmission4.ui.main.anko

import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.R.color.*
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * Created by rivaldy on 11/10/2019.
 */

class ItemLeaguesUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            verticalLayout {
                lparams(width = matchParent, height = wrapContent)
                cardView {
                    id = R.id.liga_cardv
                    foreground = getDrawable(context, R.drawable.bg_onclick_transparant)
                    setCardBackgroundColor(getColor(context, colorPrimary))
                    verticalLayout {
                        lparams(matchParent, wrapContent)
                        padding = dip(8)
                        orientation = LinearLayout.VERTICAL

                        cardView {
                            setCardBackgroundColor(getColor(context, colorTextGrey))
                            imageView {
                                id = R.id.liga_img
                                padding = dip(3)
                            }.lparams(matchParent, matchParent)
                            progressBar {
                                id = R.id.liga_progress
                            }.lparams(matchParent, matchParent) {
                                margin = dip(16)
                            }
                        }.lparams(dip(50), dip(50))

                        textView {
                            id = R.id.liga_name
                            textSize = 12f
                            textColor = getColor(context, colorAccent)
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(matchParent, wrapContent) { topMargin = dip(6) }

                        textView {
                            id = R.id.liga_desc
                            textSize = 10f
                            textColor = getColor(context, colorTextIcon)
                            maxLines = 3
                            ellipsize = TextUtils.TruncateAt.END
                        }.lparams(matchParent, wrapContent)
                    }
                }.lparams(matchParent, matchParent) {
                    topMargin = dip(2)
                    bottomMargin = dip(1)
                    rightMargin = dip(2)
                    leftMargin = dip(2)
                }
            }
        }
    }

}