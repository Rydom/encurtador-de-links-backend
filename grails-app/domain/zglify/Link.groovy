package zglify

class Link {
    String originalLink
    String shortLink
    Integer redirectCounter

    static constraints = {
        originalLink(nullable: false, blank: false)
        shortLink(nullable: false, blank: false)
    }

    static mapping = { }

}
