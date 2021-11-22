package zglify.base

import grails.gorm.transactions.Transactional
import zglify.Link

import javax.xml.bind.ValidationException

@Transactional
class DefaultService {

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
}
