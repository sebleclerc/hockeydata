package ca.sebleclerc.hockeydata.services

import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.models.CacheStep
import okhttp3.OkHttpClient
import okhttp3.Request

class CacheService(private val import: ImportService) {
  private val apiClient = OkHttpClient()

  fun cache(steps: List<CacheStep>, force: Boolean) {
    steps.forEach { cacheStep(it, force) }

    import.importRosters()
  }

  private fun cacheStep(step: CacheStep, force: Boolean) {
    deleteCacheIfNeeded(step, force)
    checkCacheAndSave(step)
  }

  private fun deleteCacheIfNeeded(step: CacheStep, force: Boolean) {
    if (force && step.file.exists()) {
      Logger.info("Deleting file at path.")
      step.file.delete()
    }
  }

  private fun checkCacheAndSave(step: CacheStep) {
    if (step.file.exists()) {
      Logger.info("Cache file exist, nothing to do.")
    } else {
      Logger.warning("Calling NHL's API")
      Logger.info(step.apiPath)
      val request = Request
        .Builder()
        .url(step.apiPath)
        .build()

      val response = apiClient.newCall(request).execute()
      val body = response.body?.string() ?: ""
      step.file.writeText(body)
    }
  }
}
