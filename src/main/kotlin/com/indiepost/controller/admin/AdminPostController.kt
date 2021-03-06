package com.indiepost.controller.admin

import com.indiepost.dto.CreateResponse
import com.indiepost.dto.DeleteResponse
import com.indiepost.dto.post.*
import com.indiepost.enums.Types
import com.indiepost.service.AdminPostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.inject.Inject
import javax.validation.ValidationException

/**
 * Created by jake on 10/8/16.
 */
@RestController
@RequestMapping(value = ["/admin/posts"], produces = ["application/json; charset=UTF-8"])
class AdminPostController @Inject
constructor(private val adminPostService: AdminPostService) {

    @GetMapping
    fun getPage(filter: PostFilter, pageable: Pageable): Page<AdminPostSummaryDto> {
        return adminPostService.getPage(filter, pageable)
    }

    @GetMapping("/{id}")
    operator fun get(@PathVariable id: Long): AdminPostResponseDto? {
        return adminPostService.findOne(id)
    }

    @PostMapping
    fun createDraft(@RequestBody dto: AdminPostRequestDto): CreateResponse {
        val id = adminPostService.createDraft(dto)
        return CreateResponse(id!!)
    }


    @PutMapping("/{id}")
    fun update(@RequestBody dto: AdminPostRequestDto, @PathVariable id: Long) {
        if (id != dto.originalId && id != dto.id) {
            throw ValidationException("REST path variable ID and AdminPostRequestDto::id are not match.")
        }
        adminPostService.update(dto)
    }

    @PostMapping("/autosave")
    fun createAutosave(@RequestBody dto: AdminPostRequestDto): CreateResponse {
        val id = adminPostService.createAutosave(dto)
        return if (dto.id != null) CreateResponse(id, dto.id) else CreateResponse(id!!)
    }

    @PutMapping("/autosave/{id}")
    fun updateAutosave(@RequestBody dto: AdminPostRequestDto, @PathVariable id: Long?) {
        if (id != dto.originalId && id != dto.id) {
            throw ValidationException("REST path variable ID and AdminPostRequestDto::id are not match.")
        }
        adminPostService.updateAutosave(dto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): DeleteResponse {
        adminPostService.deleteById(id)
        return DeleteResponse(id)
    }

    @PutMapping("/_bulk")
    fun bulkUpdate(@RequestBody dto: BulkStatusUpdateDto) {
        val status = Types.PostStatus.valueOf(dto.status.toUpperCase())
        val ids = dto.ids
        if (ids != null && ids.isNotEmpty()) {
            adminPostService.bulkStatusUpdate(ids, status)
        }
    }

    @DeleteMapping("/_bulk")
    fun bulkDelete(@RequestBody dto: BulkStatusUpdateDto) {
        val ids = dto.ids
        if (ids != null && ids.isNotEmpty()) {
            adminPostService.bulkDeleteByIds(ids)
        }
    }

    @DeleteMapping("/_trash")
    fun emptyTrash() {
        adminPostService.bulkDeleteByStatus(Types.PostStatus.TRASH)
    }
}
