package com.androidapp.appcleanarch.view.main.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidapp.appcleanarch.R
import com.androidapp.appcleanarch.Router
import com.androidapp.appcleanarch.diKoin.injectDependencies
import com.androidapp.appcleanarch.view.main.fragment.OnSearchClickListener
import com.androidapp.appcleanarch.view.main.fragment.fragmentUI.FragmentMain
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import org.koin.android.ext.android.getKoin

private const val REQUEST_CODE = 42

class ActivityMain : AppCompatActivity() {

    private val router: Router by getKoin().inject()

    private lateinit var appUpdateManager: AppUpdateManager

    //метод нужен в основном для обработки ошибок обновления
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //если все впорядке снимаем слушателя прогресса обновления
                appUpdateManager.unregisterListener(stateUpdateListener)
            } else {
                //если обновление прервано или еще какие либо ошибки, показываем уведомление
                Toast.makeText(this,
                    "Update flow failed, Result code $resultCode",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initKoin()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, FragmentMain.newInstance(), FragmentMain.TAG)
                .commit()
        }

        checkForUpdate()
    }

    private fun initKoin() {
        injectDependencies()
        router.activity = this
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            //обновление скачано но не установлено
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate()
            }
            //обновление не скачалось - можно возобновить устновку
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    REQUEST_CODE
                )
            }
        }
    }

    private fun checkForUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(application)

        //возвращаем intent для информации об обновлении
        val appUpdateInfo = appUpdateManager.appUpdateInfo

        //Проверям наличи обновления
        appUpdateInfo.addOnSuccessListener {
            //проверка на немедленное тип обновлениея(IMMEDIATE)
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                //передаем слушателя прогресса (для гибкого типа обновления)
                appUpdateManager.registerListener(stateUpdateListener)
                //выполняем запрос
                appUpdateManager.startUpdateFlowForResult(
                    it,
                    AppUpdateType.IMMEDIATE,
                    this,
                    //код для обработки запросса в onActivityResult
                    REQUEST_CODE
                )
            }
        }
    }

    private val stateUpdateListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener { state ->
            //переменная state отдает прогресс установки
            state?.let {
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                    //когда обновление скачалось обображаем SnackBar
                    popupSnackbarForCompleteUpdate()
                }
            }
        }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.activity_main_layout),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("Restart") { appUpdateManager.completeUpdate() }
            show()
        }
    }

    val searchClick = object : OnSearchClickListener {
        override fun onClick(word: String) {
            supportFragmentManager.findFragmentByTag(FragmentMain.TAG)?.let {
                (it as FragmentMain).searchClickInFragmentDialog(word)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when(newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                Toast.makeText(this, "No night mode", Toast.LENGTH_SHORT).show()
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                Toast.makeText(this, "Yes night mode", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    override fun onDestroy() {
        router.activity = null
        super.onDestroy()
    }
}