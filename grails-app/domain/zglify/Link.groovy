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

    static mapping = { }

}
