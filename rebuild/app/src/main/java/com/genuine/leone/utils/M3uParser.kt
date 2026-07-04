package com.genuine.leone.utils

import com.genuine.leone.model.ContentBean
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object M3uParser {

    fun parse(input: InputStream): List<ContentBean> {
        val reader = BufferedReader(InputStreamReader(input))
        val result = mutableListOf<ContentBean>()

        var name = ""
        var logo: String? = null
        var group: String? = null
        var tvgId: String? = null
        var userAgent: String? = null
        var referer: String? = null
        var origin: String? = null
        var cookie: String? = null
        var drmLicense: String? = null
        var drmSchema: String? = null

        reader.forEachLine { rawLine ->
            val line = rawLine.trim()
            when {
                line.startsWith("#EXTINF", ignoreCase = true) -> {
                    name = line.substringAfterLast(",").trim()
                    logo = extractAttribute(line, "tvg-logo")
                    group = extractAttribute(line, "group-title")
                    tvgId = extractAttribute(line, "tvg-id")
                }
                line.startsWith("#EXTVLCOPT:http-user-agent", ignoreCase = true) ||
                    line.contains("#KODIPROP:inputstream.adaptive.stream_headers", ignoreCase = true) -> {
                    userAgent = line.substringAfter("=").trim().ifEmpty { userAgent }
                }
                line.contains("user-agent=", ignoreCase = true) -> {
                    userAgent = line.substringAfter("=", "").trim()
                }
                line.contains("referer=", ignoreCase = true) -> {
                    referer = line.substringAfter("=", "").trim()
                }
                line.contains("origin=", ignoreCase = true) -> {
                    origin = line.substringAfter("=", "").trim()
                }
                line.contains("cookie=", ignoreCase = true) -> {
                    cookie = line.substringAfter("=", "").trim()
                }
                line.contains("license_key", ignoreCase = true) || line.contains("drm_license", ignoreCase = true) -> {
                    drmLicense = line.substringAfter("=", "").trim()
                }
                line.contains("license_type", ignoreCase = true) -> {
                    drmSchema = line.substringAfter("=", "").trim()
                }
                line.startsWith("#") -> Unit
                line.isNotBlank() -> {
                    if (drmLicense != null && drmSchema == null) {
                        drmSchema = inferDrmSchema(drmLicense)
                    }
                    result.add(
                        ContentBean(
                            name = name.ifBlank { "Unnamed" },
                            logoUrl = logo,
                            groupTitle = group,
                            streamUrl = line,
                            userAgent = userAgent,
                            referer = referer,
                            origin = origin,
                            cookie = cookie,
                            drmLicense = drmLicense,
                            drmSchema = drmSchema,
                            tvgId = tvgId
                        )
                    )
                    name = ""
                    logo = null
                    group = null
                    tvgId = null
                    userAgent = null
                    referer = null
                    origin = null
                    cookie = null
                    drmLicense = null
                    drmSchema = null
                }
            }
        }
        return result
    }

    private fun extractAttribute(line: String, key: String): String? {
        val regex = Regex("$key=\"([^\"]*)\"", RegexOption.IGNORE_CASE)
        return regex.find(line)?.groupValues?.get(1)?.ifBlank { null }
    }

    private fun inferDrmSchema(license: String): String {
        return when {
            license.contains(":") && license.length in 32..80 -> "clearkey"
            else -> "widevine"
        }
    }
}
