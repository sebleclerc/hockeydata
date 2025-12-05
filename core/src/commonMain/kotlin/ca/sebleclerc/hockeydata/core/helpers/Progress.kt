package ca.sebleclerc.hockeydata.core.helpers

interface ProgressRunner {
  fun startProgress(
    title: String,
    max: Float,
  )

  fun step()

  fun endProgress()
}

class Progress(
  val runner: ProgressRunner,
) {
  fun startProgress(
    title: String,
    max: Float,
  ) = runner.startProgress(
    title = title,
    max = max,
  )

  fun step() = runner.step()

  fun endProgress() = runner.endProgress()
}
