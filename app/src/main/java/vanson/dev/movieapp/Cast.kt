package vanson.dev.movieapp

class Cast(private var name: String, private var imgLink: Int) {

    fun getName() = this.name
    fun getImgLink() = this.imgLink

    fun setName(name: String){
        this.name = name
    }
    fun setImgLink(img_link: Int){
        this.imgLink = img_link
    }
}