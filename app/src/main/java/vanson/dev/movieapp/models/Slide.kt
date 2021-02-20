package vanson.dev.movieapp.models

class Slide(private var Image: Int, private var Title: String) {
    fun getImage(): Int{
        return Image
    }

    fun getTitle():String{
        return Title
    }

    fun setImage(image: Int){
        this.Image = image
    }

    fun setTitle(title: String){
        this.Title = title
    }
}