package com.app.vxresumebuilder.presentation.ui.fragment.resumeviewer

import android.content.Context
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintJob
import android.print.PrintManager
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.vxresumebuilder.R
import com.app.vxresumebuilder.databinding.FragmentResumeViewerBinding
import com.app.vxresumebuilder.presentation.viewmodel.viewer.ResumeViewerViewModel
import com.app.vxresumebuilder.util.Constants
import com.app.vxresumebuilder.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeViewer : Fragment(R.layout.fragment_resume_viewer) {

    private lateinit var resumeViewerBinding: FragmentResumeViewerBinding
    private val resumeViewerViewModel: ResumeViewerViewModel by viewModels()
    private var resumeId: Long? = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        resumeViewerBinding = FragmentResumeViewerBinding.bind(view)
        resumeViewerBinding.webViewResumeViewer.settings.javaScriptEnabled = true
        resumeViewerBinding.webViewResumeViewer.settings.useWideViewPort = true
        resumeViewerBinding.webViewResumeViewer.settings.setSupportZoom(true)
        resumeViewerBinding.webViewResumeViewer.settings.builtInZoomControls = true
        resumeViewerBinding.webViewResumeViewer.settings.displayZoomControls = false
        resumeViewerBinding.webViewResumeViewer.settings.loadWithOverviewMode = true
        resumeViewerBinding.fabPrintDocument.setOnClickListener {
            createWebPrintJob(resumeViewerBinding.webViewResumeViewer, "Test")
        }

        initDataCalls()
    }

    private fun loadWebViewData(webViewDataJson: String) {
        resumeViewerBinding.webViewResumeViewer.loadUrl("file:///android_asset/resumetemplateone.html")
        resumeViewerBinding.webViewResumeViewer.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, weburl: String?) {
                resumeViewerBinding.webViewResumeViewer.loadUrl("javascript:mobileAppProxyObj.renderResumeDocument($webViewDataJson)")
            }
        }
    }

    private fun initDataCalls() {
        resumeId = arguments?.getLong(Constants.DATA_FLOW_RESUME_ID)
        resumeId?.let { resumeViewerViewModel.getExistingPersonalInformation(it) }

        resumeViewerViewModel.resumeData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    loadWebViewData(it.data.toString())
                }

                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        "Error Occurred obtaining resume information",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun createWebPrintJob(webView: WebView, documentName: String) {
        // Get a PrintManager instance
        val printManager = activity
            ?.getSystemService(Context.PRINT_SERVICE) as PrintManager

        // Get a print adapter instance
        val printAdapter = webView.createPrintDocumentAdapter(documentName)

        // Create a print job with name and adapter instance
        val jobName = getString(R.string.app_name) + " Document"
        val printJob: PrintJob = printManager.print(
            jobName, printAdapter,
            PrintAttributes.Builder().build()
        )
    }
}
