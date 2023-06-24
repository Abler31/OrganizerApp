package com.my.org.presentation.resFragment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.resgraphs.RetrofitHelper
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.my.org.R
import com.my.org.data.retrofit.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResFragment : Fragment(R.layout.res_fragment) {
    lateinit var alertDialog: AlertDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bcPonF04BarChart: BarChart = view.findViewById(R.id.bc_pon_f04)
        val bcPonBarChart: BarChart = view.findViewById(R.id.bc_pon)
        val bcPovBarChart: BarChart = view.findViewById(R.id.bc_pov)
        val bcAsimF04BarChart: BarChart = view.findViewById(R.id.bc_asim_f04)
        val bcPrevLimit: BarChart = view.findViewById(R.id.bc_prev_limit)

        GlobalScope.launch(Dispatchers.Main) {

            val resultPonF04 = RetrofitHelper.resApi.getPonF04Data()
            val resultPon = RetrofitHelper.resApi.getPonData()
            val resultPov = RetrofitHelper.resApi.getPovData()
            val resultPoAsimF04 = RetrofitHelper.resApi.getAsimF04Data()
            val resultPrevLimit = RetrofitHelper.resApi.getPrevLimit()
            val index = resultPonF04.body()!!.index

            makeBarChart(
                getBarDataset1(
                    resultPonF04.body()?.data ?: emptyList(),
                    resultPonF04.body()?.columns?.get(0) ?: "0"
                ),
                getBarDataset2(
                    resultPonF04.body()?.data ?: emptyList(),
                    resultPonF04.body()?.columns?.get(1) ?: "1"
                ),
                index,
                bcPonF04BarChart
            )

            makeBarChart(
                getBarDataset1(
                    resultPon.body()?.data ?: emptyList(),
                    resultPon.body()?.columns?.get(0) ?: "0"
                ),
                getBarDataset2(
                    resultPon.body()?.data ?: emptyList(),
                    resultPon.body()?.columns?.get(1) ?: "1"
                ),
                index,
                bcPonBarChart
            )

            makeBarChart(
                getBarDataset1(
                    resultPov.body()?.data ?: emptyList(),
                    resultPov.body()?.columns?.get(0) ?: "0"
                ),
                getBarDataset2(
                    resultPov.body()?.data ?: emptyList(),
                    resultPov.body()?.columns?.get(1) ?: "1"
                ),
                index,
                bcPovBarChart
            )

            makeBarChart(
                getBarDataset1(
                    resultPoAsimF04.body()?.data ?: emptyList(),
                    resultPoAsimF04.body()?.columns?.get(0) ?: "0"
                ),
                getBarDataset2(
                    resultPoAsimF04.body()?.data ?: emptyList(),
                    resultPoAsimF04.body()?.columns?.get(1) ?: "1"
                ),
                index,
                bcAsimF04BarChart
            )

            makeBarChart(
                getBarDataset1(
                    resultPrevLimit.body()?.data ?: emptyList(),
                    resultPrevLimit.body()?.columns?.get(0) ?: "0"
                ),
                getBarDataset2(
                    resultPrevLimit.body()?.data ?: emptyList(),
                    resultPrevLimit.body()?.columns?.get(1) ?: "1"
                ),
                index,
                bcPrevLimit
            )
        }
    }

    private fun getBarDataset1(data: List<List<Any>>, column: String): BarDataSet {
        val barEntries1 = ArrayList<BarEntry>()
        for (i in 0..8) {
            val x: Float = data[i][0].toString().toFloat()
            barEntries1.add(
                BarEntry(
                    (i + 1).toFloat(),
                    (data[i][0].toString().toFloat()) ?: 0f
                )
            )
        }
        val barDataSet1 = BarDataSet(barEntries1, column ?: "0")
        barDataSet1.color = Color.BLACK
        return barDataSet1
    }

    private fun getBarDataset2(data: List<List<Any>>, column: String): BarDataSet {
        val barEntries2 = ArrayList<BarEntry>()
        for (i in 0..8) {
            barEntries2.add(
                BarEntry(
                    (i + 1).toFloat(),
                    (data[i][1].toString().toFloat()) ?: 0f
                )
            )
        }
        val barDataSet2 = BarDataSet(barEntries2, column ?: "2")
        barDataSet2.color = activity?.resources?.getColor(R.color.greyW) ?: Color.GRAY
        return barDataSet2
    }

    private fun makeBarChart(
        barDataSet1: BarDataSet,
        barDataSet2: BarDataSet,
        index: List<String>,
        mBarChart: BarChart
    ) {
        val data = BarData(barDataSet1, barDataSet2)
        mBarChart.data = data
        val xAxis = mBarChart.xAxis
        val leftAxis = mBarChart.axisLeft
        val rightAxis = mBarChart.axisRight

        xAxis.valueFormatter = IndexAxisValueFormatter(index)
        xAxis.setCenterAxisLabels(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true


        mBarChart.isDragEnabled = true
        mBarChart.setVisibleXRangeMaximum(9f)
        mBarChart.description.isEnabled = false
        mBarChart.setDrawGridBackground(false)

        val barSpace = 0.07f
        val groupSpace = 0.46f

        data.barWidth = 0.2f

        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = 0 + mBarChart.barData.getGroupWidth(groupSpace, barSpace) * 9
        xAxis.setDrawGridLines(false)
        xAxis.gridColor = Color.RED
        xAxis.textColor = Color.GRAY
        xAxis.textSize = 8f
        xAxis.setLabelCount(index.size)

        leftAxis.axisMinimum = 0f
        leftAxis.textColor = Color.GRAY
        leftAxis.textSize = 12f
        leftAxis.gridLineWidth = 0.4f
        leftAxis.setDrawAxisLine(false)

        rightAxis.setDrawLabels(false)
        rightAxis.setDrawGridLines(false)
        rightAxis.setDrawAxisLine(false)

        mBarChart.legend.textColor = Color.GRAY
        mBarChart.legend.textSize = 15f

        barDataSet1.valueTextSize = 8f
        barDataSet2.valueTextSize = 8f
        barDataSet1.valueTextColor = Color.GRAY
        barDataSet2.valueTextColor = Color.GRAY
        barDataSet1.valueFormatter = DefaultValueFormatter(0)
        barDataSet2.valueFormatter = DefaultValueFormatter(0)

        mBarChart.setExtraOffsets(5f, 5f, 5f, 15f)
        mBarChart.groupBars(0f, groupSpace, barSpace)
        mBarChart.isHighlightPerDragEnabled = false
        mBarChart.animateY(500)

        fun makeBranchesDialog(response: Data?, resName: String, mBarDataSet: IBarDataSet) {
            //количество элементов,соответствующих выбранной РЭС
            val elementsCount = response?.index?.filter { it == resName }?.size
            Log.d("test", "elements count:" + elementsCount.toString())
            //первое вхождение названия в списке
            val firstElementPosition = response?.index?.indexOf(resName)
            Log.d("test", "position: " + firstElementPosition.toString())
            val view = LayoutInflater.from(context).inflate(R.layout.res_dialog_layout, null)
            val dialogLayout = view.findViewById<LinearLayout>(R.id.dialog_layout)
            for (i in firstElementPosition!! until firstElementPosition + elementsCount!!) {
                val dataList = response.data[i]
                val columnsList = response.columns
                val branchView =
                    LayoutInflater.from(context).inflate(R.layout.res_branches_item, null)
                //выбор второго столбца либо первого
                if (mBarDataSet == barDataSet2) {
                    dialogLayout.findViewById<TextView>(R.id.tv_branches_dialog_month).text =
                        columnsList[2]
                    dialogLayout.findViewById<TextView>(R.id.tv_branches_dialog_res).text = resName
                    branchView.findViewById<TextView>(R.id.tv_branch_res).text =
                        dataList[0].toString()
                    branchView.findViewById<TextView>(R.id.tv_branch_red_zone).text =
                        dataList[2].toString().split(".")[0]
                    branchView.findViewById<TextView>(R.id.tv_branch_control).text =
                        dataList[4].toString().split(".")[0]
                } else {
                    dialogLayout.findViewById<TextView>(R.id.tv_branches_dialog_month).text =
                        columnsList[1]
                    dialogLayout.findViewById<TextView>(R.id.tv_branches_dialog_res).text = resName
                    branchView.findViewById<TextView>(R.id.tv_branch_res).text =
                        dataList[0].toString()
                    branchView.findViewById<TextView>(R.id.tv_branch_red_zone).text =
                        dataList[1].toString().split(".")[0]
                    branchView.findViewById<TextView>(R.id.tv_branch_control).text =
                        dataList[3].toString().split(".")[0]
                }
                dialogLayout.addView(branchView)
            }
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(true)

            builder.setView(view)
            alertDialog = builder.create()
            alertDialog.show()
        }

        mBarChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val resArray =
                    arrayOf("АЭС", "БуЭС", "БЭС", "ЕЭС", "КЭС", "НКЭС", "НЧЭС", "ПЭС", "ЧЭС")
                val x = mBarChart.barData.getDataSetForEntry(e).getEntryIndex(e as BarEntry?)
                val mBarDataSet = mBarChart.barData.getDataSetForEntry(e)
                val resName = resArray[x]

                GlobalScope.launch(Dispatchers.Main) {
                    var response = RetrofitHelper.resApi.getPonF04BranchData().body()
                    when (mBarChart.id) {
                        R.id.bc_pon -> response = RetrofitHelper.resApi.getPonBranchData().body()
                        R.id.bc_pov -> response = RetrofitHelper.resApi.getPovBranchData().body()
                        R.id.bc_pon_f04 -> response = RetrofitHelper.resApi.getPonF04BranchData().body()
                        R.id.bc_asim_f04 -> response = RetrofitHelper.resApi.getAsimF04BranchData().body()
                        R.id.bc_prev_limit -> response = RetrofitHelper.resApi.getPrevLimitBranch().body()
                    }
                    makeBranchesDialog(response, resName, mBarDataSet)
                }
            }

            override fun onNothingSelected() {

            }
        })
        mBarChart.invalidate()
    }
}