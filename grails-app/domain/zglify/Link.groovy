package zglify

class Link {
    String originalLink
    String shortLink
    Integer redirectCounter

    Link() {
        if (this.redirectCounter == null) {
            this.redirectCounter = 0
        }
    }

    static constraints = {
        originalLink(nullable: false, blank: false)
        shortLink(nullable: false, blank: false)
    }

    static mapping = {
        // O intuito do cache é aumentar a velocidade de resposta / redirecionammento
        cache usage: 'read-only', include: 'non-lazy'
    }

}
