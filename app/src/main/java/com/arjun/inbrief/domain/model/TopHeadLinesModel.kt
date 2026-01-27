package com.arjun.inbrief.domain.model

/*
responsibilities of domain model
1️⃣ Represents business data (independent of api or Ui structure)
2️⃣ Pure Kotlin (only data+types)
3️⃣ Stable & long-living (rarely changes not effected by api response changes)
4️⃣ Non-nullable by design (null handling done in mapper so no issues with null)
✔ No annotations
 */

//here eliminating nested data classes like sources and realtime articles because these are not much needed. so we are flattening in mapping.
data class TopHeadLinesModel(
    val articles: List<Article>,
    val information: String, //changed because it does not need separate data class
    val totalArticles: Int
) {
    data class Article(
        val content: String ="",
        val description: String ="",
        val id: String= "",
        val image: String ="",
        val lang: String ="",
        val publishedAt: String ="",
        val source: String ="",
        val title: String ="",
        val url: String =""
    )
}