package tgo1014.desafioandroid.model

data class Shot(var id: Int = 0,
                var title: String = "",
                var description: String = "",
                var views_count: Int = 0,
                var likes_count: Int = 0,
                var comments_count: Int = 0,
                var images: Images = Images())