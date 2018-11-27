package com.indiepost.mapper

import com.indiepost.dto.ProfileDto
import com.indiepost.enums.Types
import com.indiepost.model.Profile
import com.indiepost.utils.DateUtil.localDateTimeToInstant
import java.time.LocalDateTime

fun Profile.createDto(): ProfileDto {
    val dto = ProfileDto()
    dto.id = id
    dto.description = description
    dto.showDescription = showDescription
    dto.displayName = displayName
    dto.etc = etc
    dto.fullName = fullName
    dto.label = label
    dto.showLabel = showLabel
    dto.profileState = profileState.toString()
    dto.profileType = profileType.toString()
    dto.email = email
    dto.subEmail = subEmail
    dto.showEmail = showEmail
    dto.slug = slug
    created?.let {
        dto.created = localDateTimeToInstant(it)
    }
    lastUpdated?.let {
        dto.lastUpdated = localDateTimeToInstant(it)
    }
    return dto
}

// TODO
fun ProfileDto.createEntiry(): Profile {
    val profile = Profile(slug = this.slug!!)
    profile.created = LocalDateTime.now()
    profile.lastUpdated = LocalDateTime.now()
    profile.description = description
    profile.email = email
    profile.subEmail = subEmail
    profile.label = label
    profile.phone = phone
    profile.picture = picture
    profile.etc = etc
    profile.showDescription = showDescription
    profile.showEmail = showEmail
    profile.showLabel = showLabel
    profile.showPicture = false
    profileState?.let {
        profile.profileState = Types.ProfileState.valueOf(it)
    }
    profileType?.let {
        profile.profileType = Types.ProfileType.valueOf(it)

    }
    displayName?.let {
        profile.displayName = it
    }
    fullName?.let {
        profile.fullName = it
    }
    return profile
}

fun Profile.merge(profile: Profile) {
    slug = profile.slug
    fullName = profile.fullName
    description = profile.description
    showDescription = profile.showDescription
    displayName = profile.displayName
    showLabel = profile.showLabel
    picture = profile.picture
    showPicture = profile.showPicture
    etc = profile.etc
    email = profile.email
    subEmail = profile.subEmail
    showEmail = profile.showEmail
    profileType = profile.profileType
    profileState = profile.profileState
    label = profile.label
    phone = profile.phone
    lastUpdated = LocalDateTime.now()
}

