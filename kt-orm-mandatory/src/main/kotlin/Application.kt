import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    val database = Database.connect(
        "jdbc:mysql://localhost:3306/mandatory_assignment?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
        driver = "com.mysql.jdbc.Driver", user = "<user>", password = "<password>"
    )

    transaction {
        // print sql to std-out
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(FavouriteAudioBooks)

        // CREATE
        val favId = FavouriteAudioBooks.insertAndGetId {
            it[title] = "B"
            it[author] = "BB"
            it[narrator] = "BBB"
            it[genre] = "BBBB"
        }
        println(favId)

        // READ
        FavouriteAudioBooks
            .select { FavouriteAudioBooks.id eq 1 }
            .forEach {
                println(it[FavouriteAudioBooks.title])
            }

        // Distinct values
        val narrators = FavouriteAudioBooks
            .slice(FavouriteAudioBooks.title)
            .select { FavouriteAudioBooks.id lessEq 10 }
            .withDistinct()
            .map {
                it[FavouriteAudioBooks.title]
            }
        narrators.forEach { println(it) }

        // UPDATE
        FavouriteAudioBooks.update(where = { FavouriteAudioBooks.id eq 3 }) {
            it[title] = "C"
            it[author] = "CC"
            it[narrator] = "CCC"
            it[genre] = "CCCC"
        }

        // DELETE
        FavouriteAudioBooks.deleteWhere { FavouriteAudioBooks.id eq 4 }
    }
}

object FavouriteAudioBooks : IntIdTable() {
    val title: Column<String> = varchar("title", 250)
    val author: Column<String> = varchar("author", 250)
    val narrator: Column<String> = varchar("narrator", 250)
    val genre: Column<String> = varchar("genre", 75)
}

//object Genres: Table() {
//    val genreId = integer("genre_id").autoIncrement()
//    val genre = varchar("genre", 75)
//    override val primaryKey = PrimaryKey(genreId, name = "pk_genre_id")
//}



