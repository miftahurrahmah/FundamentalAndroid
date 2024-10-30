package com.example.submissionfundamental.adapter

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.submissionfundamental.data.response.ListEventsItem
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "events")
data class Event(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,

    @ColumnInfo(name = "imageLogo")
    val imageLogo: String?,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "ownerName")
    val ownerName: String,

    @ColumnInfo(name = "beginTime")
    val beginTime: String,

    @ColumnInfo(name = "endTime")
    val endTime: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "quota")
    val quota: Int,

    @ColumnInfo(name = "regostrant")
    val registrants: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "link")
    val link: String,

    @ColumnInfo(name = "cityName")
    val cityName: String,

    @ColumnInfo(name = "summary")
    val summary: String
) : Parcelable {
    companion object {
        fun fromListEventsItem(item: ListEventsItem): Event {
            return Event(
                id = item.id,
                title = item.name,
                date = item.beginTime,
                imageUrl = item.imageLogo,
                imageLogo = item.imgUrl,
                name = item.name,
                ownerName = item.ownerName,
                beginTime = item.beginTime,
                endTime = item.endTime,
                category = item.category,
                quota = item.quota,
                registrants = item.registrants,
                description = item.description,
                link = item.link,
                cityName = item.cityName,
                summary = item.summary
            )
        }
    }
}
