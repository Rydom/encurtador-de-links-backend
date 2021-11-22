package zglify

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import org.apache.commons.lang.RandomStringUtils

import javax.xml.bind.ValidationException

@Service(Link)
abstract class LinkService {

//    abstract Link get(Serializable id)

//    abstract List<Link> list(Map args)

    abstract Long count()

//    abstract void delete(Serializable id)

//    abstract Link save(Link link)

    def list(Map params) {
        try {
            List<Link> links = Link.createCriteria().list(max: params.max, offSet: params.offSet) {
                order(params.order, params.sort)
            }
            return links
        } catch (e) {
            throw new RuntimeException("Falha ao listar", e.getMessage())
        }
    }

    def get(Serializable id) {
        try {
            Link link = Link.findById(id)
            return link
        } catch (e) {
            throw new RuntimeException("Falha ao buscar registro", e.getMessage())
        }
    }

    def save(Link link) {
        Link.withTransaction { status ->
            try {
                link.save(flush: true, failOnError: true)
            } catch (ValidationException e) {
                status.setRollbackOnly()
                throw new RuntimeException("Falha ao salvar", e.getMessage())
            }
        }
    }

    def update(Link link) {
        Link.withTransaction { status ->
            try {
                link.save(flush: true, failOnError: true)
            } catch (ValidationException e) {
                status.setRollbackOnly()
                throw new RuntimeException("Falha ao atualizar", e.getMessage())
            }
        }
    }

    def delete(Serializable id) {
        Link.withTransaction { status ->
            try {
                def link = Link.findById(id)
                link.delete(flush: true, failOnError: true)

            } catch (Exception e) {
                throw new RuntimeException("Falha ao deletar", e.getMessage())
            }
        }
    }

    @Transactional
    Link register(Link link) {
        if (!link.originalLink.contains('http://') || !link.originalLink.contains('https://')) {
            link.originalLink = 'https://' + link.originalLink
        }
        link.shortLink = generateShortLink()
        save(link)

        return link
    }

    String getRedirectUrl(String shortLink) {
        Link link = Link.createCriteria().get {
            eq('shortLink', shortLink)
        }

        return link?.originalLink

    }

    private String generateShortLink() {
        def randomUrlLength = 5
        def randomShortUrl = RandomStringUtils.randomAlphanumeric(randomUrlLength)

        return randomShortUrl
    }

}
