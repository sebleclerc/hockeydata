package ca.sebleclerc.hockeydata.cache

import ca.sebleclerc.hockeydata.core.cache.CacheStep
import ca.sebleclerc.hockeydata.core.helpers.Logger
import ca.sebleclerc.hockeydata.core.helpers.Progress
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.collections.forEach

class CacheService(
  private val import: ImportService,
  private val progress: Progress,
) {
  private val apiClient = OkHttpClient()

  fun cache(
    steps: List<CacheStep>,
    force: Boolean,
    showProgress: Boolean = false,
  ) {
    steps.forEach {
      cacheStep(it, force)
      if (showProgress) {
        progress.step()
      }
    }

    import.importRosters()
  }

  private fun cacheStep(
    step: CacheStep,
    force: Boolean,
  ) {
    deleteCacheIfNeeded(step, force)
    checkCacheAndSave(step)
  }

  private fun deleteCacheIfNeeded(
    step: CacheStep,
    force: Boolean,
  ) {
    if (force && step.file.exists()) {
      Logger.debug("Deleting file at path.")
      step.file.delete()
    }
  }

  private fun checkCacheAndSave(step: CacheStep) {
    if (step.file.exists()) {
      Logger.debug("Cache file exist, nothing to do.")
    } else {
      Logger.warning("Calling NHL's API")
      Logger.debug(step.apiPath)
      val request =
        Request
          .Builder()
          .url(step.apiPath)
          .build()

      val response = apiClient.newCall(request).execute()
      val body = response.body?.string() ?: ""
      step.file.writeText(body)
    }
  }
}