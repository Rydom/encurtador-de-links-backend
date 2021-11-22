package zglify


import grails.validation.ValidationException
import org.springframework.http.HttpStatus

class LinkController {
	static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    LinkService linkService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond linkService.list(params), [linkCount:  linkService.count(), view: 'index']
    }

    def show(Long id) {
        respond linkService.get(id)
    }

    def save(Link link) {
        if (link == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }

        try {
            linkService.register(link)
        } catch (Exception e) {
            respond link.errors, view:'create'
            return
        }
        respond link, [status: HttpStatus.CREATED, view:"show"]
    }

    def update(Link link) {
        if (link == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }

        try {
            linkService.save(link)
        } catch (ValidationException e) {
            respond link.errors, view:'edit'
            return
        }

        respond link, [status: HttpStatus.OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }

        linkService.delete(id)

        render status: HttpStatus.NO_CONTENT
    }
}
