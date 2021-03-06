/*
    Copyright (c) 2014 Igor Tseglevskiy <igor.tseglevskiy@gmail.com>
    Copyright (c) 2014 Other contributors as noted in the AUTHORS file.

    Этот файл является частью приложения "Мобильное рабочее место оператора
    Горячей линии по пропавшим детям".

    Данная лицензия разрешает лицам, получившим копию "Мобильного рабочего
    места оператора Горячей линии по пропавшим детям" и сопутствующей
    документации (в дальнейшем именуемыми «Программное Обеспечение»),
    безвозмездно использовать Программное Обеспечение без ограничений, включая
    неограниченное право на использование, копирование, изменение, добавление,
    публикацию, распространение, сублицензирование и/или продажу копий
    Программного Обеспечения, также как и лицам, которым предоставляется данное
    Программное Обеспечение, при соблюдении следующих условий:

    Указанное выше уведомление об авторском праве и данные условия должны быть
    включены во все копии или значимые части данного Программного Обеспечения.

    ДАННОЕ ПРОГРАММНОЕ ОБЕСПЕЧЕНИЕ ПРЕДОСТАВЛЯЕТСЯ «КАК ЕСТЬ», БЕЗ КАКИХ-ЛИБО
    ГАРАНТИЙ, ЯВНО ВЫРАЖЕННЫХ ИЛИ ПОДРАЗУМЕВАЕМЫХ, ВКЛЮЧАЯ, НО НЕ
    ОГРАНИЧИВАЯСЬ ГАРАНТИЯМИ ТОВАРНОЙ ПРИГОДНОСТИ, СООТВЕТСТВИЯ ПО ЕГО
    КОНКРЕТНОМУ НАЗНАЧЕНИЮ И ОТСУТСТВИЯ НАРУШЕНИЙ ПРАВ. НИ В КАКОМ СЛУЧАЕ
    АВТОРЫ ИЛИ ПРАВООБЛАДАТЕЛИ НЕ НЕСУТ ОТВЕТСТВЕННОСТИ ПО ИСКАМ О ВОЗМЕЩЕНИИ
    УЩЕРБА, УБЫТКОВ ИЛИ ДРУГИХ ТРЕБОВАНИЙ ПО ДЕЙСТВУЮЩИМ КОНТРАКТАМ, ДЕЛИКТАМ
    ИЛИ ИНОМУ, ВОЗНИКШИМ ИЗ, ИМЕЮЩИМ ПРИЧИНОЙ ИЛИ СВЯЗАННЫМ С ПРОГРАММНЫМ
    ОБЕСПЕЧЕНИЕМ ИЛИ ИСПОЛЬЗОВАНИЕМ ПРОГРАММНОГО ОБЕСПЕЧЕНИЯ ИЛИ ИНЫМИ
    ДЕЙСТВИЯМИ С ПРОГРАММНЫМ ОБЕСПЕЧЕНИЕМ.

    Кроме содержимого в этом уведомлении, ни название "Горячей линии по
    пропавшим детям", ни название "Добровольного поискового отряда "Лиза
    Алерт", ни имена вышеуказанных держателей авторских прав не должно быть
    использовано в рекламе или иным способом, чтобы увеличивать продажу,
    использование или другие работы в этом Программном обеспечении без
    предшествующего письменного разрешения.

    Permission is hereby granted, free of charge, to any person obtaining a
    copy of this software and associated documentation files (the "Software"),
    to deal in the Software without restriction, including without limitation
    the rights to use, copy, modify, merge, publish, distribute, sublicense,
    and/or sell copies of the Software, and to permit persons to whom the
    Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
    THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
    FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
    DEALINGS IN THE SOFTWARE.

    Except as contained in this notice, the name of Liza Alert or the name of
    Liza Alerts's hotline department or the name(s) the above copyright holders
    shall not be used in advertising or otherwise to promote the sale, use or
    other dealings in this Software without prior written authorization.
 */

package ru.lizaalert.common.ui

import java.io.{IOException, InputStreamReader, BufferedReader}

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.{MenuItem, View}
import android.widget.{LinearLayout, ImageView, TextView}
import com.yandex.metrica.YandexMetrica
import ru.lizaalert.common.{BuildConfig, R}
import java.lang.StringBuilder

class About extends Activity {
  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_about)
    getActionBar.setHomeButtonEnabled(true)
    getActionBar.setDisplayHomeAsUpEnabled(true)

    val pInfo = getPackageManager().getPackageInfo(getPackageName(), 0)

    val am = getAssets



    val sb = (new StringBuilder)
      .append(getString(R.string.msg_about))
      .append("\n\n")
      .append(getString(R.string.msg_version))
      .append(" ")
      .append(pInfo.versionName)
      .append(" (")
      .append(BuildConfig.VERSION_CODE)
      .append(")")

    findViewById(R.id.about_version)
      .asInstanceOf[TextView]
      .setText(sb.toString)

    val authors = findViewById(R.id.about_authors).asInstanceOf[TextView]
    val authorsLayout = findViewById(R.id.authors_layout).asInstanceOf[LinearLayout]
    val iAuthorsOpen = findViewById(R.id.ico_authors_open).asInstanceOf[ImageView]
    val iAuthorsClose = findViewById(R.id.ico_authors_close).asInstanceOf[ImageView]
    val authorsText = readFileFromAssets("AUTHORS")
    if (authorsText != null) {
      authorsLayout.setOnClickListener(new View.OnClickListener() {
        def onClick(v: View): Unit = {
          toggleTV(authors, iAuthorsOpen, iAuthorsClose)
        }
      })
      authors.setText(authorsText)
    } else {
      authors.setVisibility(View.GONE)
    }

    val license = findViewById(R.id.about_license).asInstanceOf[TextView]
    val licenseLayout = findViewById(R.id.license_layout).asInstanceOf[LinearLayout]
    val iLicenseOpen = findViewById(R.id.ico_license_open).asInstanceOf[ImageView]
    val iLicenseClose = findViewById(R.id.ico_license_close).asInstanceOf[ImageView]
    val licenseText = readFileFromAssets("LICENSE")
    if (licenseText != null) {
      license.setText(licenseText)
      licenseLayout.setOnClickListener(new View.OnClickListener() {
        def onClick(v: View): Unit = {
          toggleTV(license, iLicenseOpen, iLicenseClose)
        }
      })
    } else {
      licenseLayout.setVisibility(View.GONE)
    }

    //    val scalaTextView = findViewById(R.id.scala_text_view).asInstanceOf[TextView]
//    scalaTextView.setText(new HelloJava().say())

    Log.d("debug", "HelloActivity onCreate")
  }

  override def onOptionsItemSelected(menuItem: MenuItem): Boolean = {
    menuItem.getItemId match {
      case android.R.id.home =>
        onBackPressed
        return true
    }
    return (super.onOptionsItemSelected(menuItem))
  }

  def toggleTV (view:TextView, iOpen:ImageView, iClose:ImageView) = {
    if (view.getVisibility == View.VISIBLE) {
      view.setVisibility(View.GONE)
      iOpen.setVisibility(View.GONE)
      iClose.setVisibility(View.VISIBLE)
    } else {
      view.setVisibility(View.VISIBLE)
      iOpen.setVisibility(View.VISIBLE)
      iClose.setVisibility(View.GONE)
    }
  }

  private def readFileFromAssets(fileName: String): String = {
    try {
      val reader = new BufferedReader(new InputStreamReader(getAssets.open(fileName), "UTF-8"))
      val sb = new java.lang.StringBuilder

      var s = reader.readLine
      while (s != null) {
        sb.append(s)
        sb.append(System.getProperty("line.separator"))
        s = reader.readLine
      }

//      Iterator
//        .continually(reader.readLine)
//        .takeWhile(null !=)
//        .foreach(sb.append)

      reader.close
      return sb.toString
    }
    catch {
      case e: IOException => {
        Log.e("8800", "couldn't open $fileName")
        e.printStackTrace
      }
    }

    return null
  }

  override def onResume() = {
    super.onResume()
    YandexMetrica.onResumeActivity(this)
  }

  override def onPause() = {
    super.onPause()
    YandexMetrica.onResumeActivity(this)
  }
}
