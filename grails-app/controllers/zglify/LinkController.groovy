package zglify


import grails.validation.ValidationException
import org.springframework.http.HttpStatus

class LinkController {
	static responseFormats = ['json', 'xml']
    static allowedMethods = [redirect: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    LinkService linkService

    def index(Integer max) {
        params.max = params.max ?: Math.min(max ?: 15, 100)
        params.offSet = params.offSet ?: 0
        params.order =  params.order ?: 'id'
        params.sort = params.sort ?: 'desc'

        List<Link> links = linkService.list(params)
        def count = linkService.count()
        Map data = [rows: links, count: count]

        respond data, view: 'index'
    }
    def getDetails() {
        def details = linkService.getDetails()
        respond details
    }
    def save(Link link) {
        if (link == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }
        try {
            linkService.register(link)
        } catch (ValidationException e) {
            respond link.errors, view:'create'
            return
        }

        respond link, [status: HttpStatus.CREATED, view:"show"]
    }
    def redirect() {
        // TODO incrementar o contador redirectCounter
        String shortLink = params.shortLink
        def originalLink = linkService.getRedirectUrl(shortLink)

        if (originalLink == null) {
            Map message = [message: "Não foi possível encontrar o link original"]
            respond message, status: HttpStatus.NOT_FOUND
            return
        }

        redirect(uri: originalLink)
    }
}
