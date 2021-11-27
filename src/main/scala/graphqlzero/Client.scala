package graphqlzero

import caliban.client.CalibanClientError.DecodingError
import caliban.client.FieldBuilder._
import caliban.client._
import caliban.client.__Value._

object Client {

  type Upload = String

  sealed trait CacheControlScope extends scala.Product with scala.Serializable {
    def value: String
  }
  object CacheControlScope {
    case object PUBLIC extends CacheControlScope {
      val value: String = "PUBLIC"
    }
    case object PRIVATE extends CacheControlScope {
      val value: String = "PRIVATE"
    }

    implicit val decoder: ScalarDecoder[CacheControlScope] = {
      case __StringValue("PUBLIC")  => Right(CacheControlScope.PUBLIC)
      case __StringValue("PRIVATE") => Right(CacheControlScope.PRIVATE)
      case other =>
        Left(DecodingError(s"Can't build CacheControlScope from input $other"))
    }
    implicit val encoder: ArgEncoder[CacheControlScope] = {
      case CacheControlScope.PUBLIC  => __EnumValue("PUBLIC")
      case CacheControlScope.PRIVATE => __EnumValue("PRIVATE")
    }

    val values: Vector[CacheControlScope] = Vector(PUBLIC, PRIVATE)
  }

  sealed trait OperatorKindEnum extends scala.Product with scala.Serializable {
    def value: String
  }
  object OperatorKindEnum {
    case object GTE extends OperatorKindEnum { val value: String = "GTE" }
    case object LTE extends OperatorKindEnum { val value: String = "LTE" }
    case object NE extends OperatorKindEnum { val value: String = "NE" }
    case object LIKE extends OperatorKindEnum { val value: String = "LIKE" }

    implicit val decoder: ScalarDecoder[OperatorKindEnum] = {
      case __StringValue("GTE")  => Right(OperatorKindEnum.GTE)
      case __StringValue("LTE")  => Right(OperatorKindEnum.LTE)
      case __StringValue("NE")   => Right(OperatorKindEnum.NE)
      case __StringValue("LIKE") => Right(OperatorKindEnum.LIKE)
      case other =>
        Left(DecodingError(s"Can't build OperatorKindEnum from input $other"))
    }
    implicit val encoder: ArgEncoder[OperatorKindEnum] = {
      case OperatorKindEnum.GTE  => __EnumValue("GTE")
      case OperatorKindEnum.LTE  => __EnumValue("LTE")
      case OperatorKindEnum.NE   => __EnumValue("NE")
      case OperatorKindEnum.LIKE => __EnumValue("LIKE")
    }

    val values: Vector[OperatorKindEnum] = Vector(GTE, LTE, NE, LIKE)
  }

  sealed trait SortOrderEnum extends scala.Product with scala.Serializable {
    def value: String
  }
  object SortOrderEnum {
    case object ASC extends SortOrderEnum { val value: String = "ASC" }
    case object DESC extends SortOrderEnum { val value: String = "DESC" }

    implicit val decoder: ScalarDecoder[SortOrderEnum] = {
      case __StringValue("ASC")  => Right(SortOrderEnum.ASC)
      case __StringValue("DESC") => Right(SortOrderEnum.DESC)
      case other =>
        Left(DecodingError(s"Can't build SortOrderEnum from input $other"))
    }
    implicit val encoder: ArgEncoder[SortOrderEnum] = {
      case SortOrderEnum.ASC  => __EnumValue("ASC")
      case SortOrderEnum.DESC => __EnumValue("DESC")
    }

    val values: Vector[SortOrderEnum] = Vector(ASC, DESC)
  }

  type Address
  object Address {

    final case class AddressView[GeoSelection](
        street: Option[String],
        suite: Option[String],
        city: Option[String],
        zipcode: Option[String],
        geo: Option[GeoSelection]
    )

    type ViewSelection[GeoSelection] =
      SelectionBuilder[Address, AddressView[GeoSelection]]

    def view[GeoSelection](
        geoSelection: SelectionBuilder[Geo, GeoSelection]
    ): ViewSelection[GeoSelection] =
      (street ~ suite ~ city ~ zipcode ~ geo(geoSelection)).map {
        case ((((street, suite), city), zipcode), geo) =>
          AddressView(street, suite, city, zipcode, geo)
      }

    def street: SelectionBuilder[Address, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("street", OptionOf(Scalar()))
    def suite: SelectionBuilder[Address, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("suite", OptionOf(Scalar()))
    def city: SelectionBuilder[Address, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("city", OptionOf(Scalar()))
    def zipcode: SelectionBuilder[Address, Option[String]] =
      _root_.caliban.client.SelectionBuilder
        .Field("zipcode", OptionOf(Scalar()))
    def geo[A](
        innerSelection: SelectionBuilder[Geo, A]
    ): SelectionBuilder[Address, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("geo", OptionOf(Obj(innerSelection)))
  }

  type Album
  object Album {

    final case class AlbumView[UserSelection, PhotosSelection](
        id: Option[String],
        title: Option[String],
        user: Option[UserSelection],
        photos: Option[PhotosSelection]
    )

    type ViewSelection[UserSelection, PhotosSelection] =
      SelectionBuilder[Album, AlbumView[UserSelection, PhotosSelection]]

    def view[UserSelection, PhotosSelection](
        photosOptions: Option[PageQueryOptions] = None
    )(
        userSelection: SelectionBuilder[User, UserSelection],
        photosSelection: SelectionBuilder[PhotosPage, PhotosSelection]
    ): ViewSelection[UserSelection, PhotosSelection] = (id ~ title ~ user(
      userSelection
    ) ~ photos(photosOptions)(photosSelection)).map {
      case (((id, title), user), photos) => AlbumView(id, title, user, photos)
    }

    def id: SelectionBuilder[Album, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("id", OptionOf(Scalar()))
    def title: SelectionBuilder[Album, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("title", OptionOf(Scalar()))
    def user[A](
        innerSelection: SelectionBuilder[User, A]
    ): SelectionBuilder[Album, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("user", OptionOf(Obj(innerSelection)))
    def photos[A](
        options: Option[PageQueryOptions] = None
    )(innerSelection: SelectionBuilder[PhotosPage, A])(implicit
        encoder0: ArgEncoder[Option[PageQueryOptions]]
    ): SelectionBuilder[Album, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "photos",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("options", options, "PageQueryOptions")(encoder0))
      )
  }

  type AlbumsPage
  object AlbumsPage {

    final case class AlbumsPageView[
        DataSelection,
        LinksSelection,
        MetaSelection
    ](
        data: Option[List[Option[DataSelection]]],
        links: Option[LinksSelection],
        meta: Option[MetaSelection]
    )

    type ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      SelectionBuilder[
        AlbumsPage,
        AlbumsPageView[DataSelection, LinksSelection, MetaSelection]
      ]

    def view[DataSelection, LinksSelection, MetaSelection](
        dataSelection: SelectionBuilder[Album, DataSelection],
        linksSelection: SelectionBuilder[PaginationLinks, LinksSelection],
        metaSelection: SelectionBuilder[PageMetadata, MetaSelection]
    ): ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      (data(dataSelection) ~ links(linksSelection) ~ meta(metaSelection)).map {
        case ((data, links), meta) => AlbumsPageView(data, links, meta)
      }

    def data[A](
        innerSelection: SelectionBuilder[Album, A]
    ): SelectionBuilder[AlbumsPage, Option[List[Option[A]]]] =
      _root_.caliban.client.SelectionBuilder
        .Field("data", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))
    def links[A](
        innerSelection: SelectionBuilder[PaginationLinks, A]
    ): SelectionBuilder[AlbumsPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("links", OptionOf(Obj(innerSelection)))
    def meta[A](
        innerSelection: SelectionBuilder[PageMetadata, A]
    ): SelectionBuilder[AlbumsPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("meta", OptionOf(Obj(innerSelection)))
  }

  type Comment
  object Comment {

    final case class CommentView[PostSelection](
        id: Option[String],
        name: Option[String],
        email: Option[String],
        body: Option[String],
        post: Option[PostSelection]
    )

    type ViewSelection[PostSelection] =
      SelectionBuilder[Comment, CommentView[PostSelection]]

    def view[PostSelection](
        postSelection: SelectionBuilder[Post, PostSelection]
    ): ViewSelection[PostSelection] =
      (id ~ name ~ email ~ body ~ post(postSelection)).map {
        case ((((id, name), email), body), post) =>
          CommentView(id, name, email, body, post)
      }

    def id: SelectionBuilder[Comment, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("id", OptionOf(Scalar()))
    def name: SelectionBuilder[Comment, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("name", OptionOf(Scalar()))
    def email: SelectionBuilder[Comment, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("email", OptionOf(Scalar()))
    def body: SelectionBuilder[Comment, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("body", OptionOf(Scalar()))
    def post[A](
        innerSelection: SelectionBuilder[Post, A]
    ): SelectionBuilder[Comment, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("post", OptionOf(Obj(innerSelection)))
  }

  type CommentsPage
  object CommentsPage {

    final case class CommentsPageView[
        DataSelection,
        LinksSelection,
        MetaSelection
    ](
        data: Option[List[Option[DataSelection]]],
        links: Option[LinksSelection],
        meta: Option[MetaSelection]
    )

    type ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      SelectionBuilder[
        CommentsPage,
        CommentsPageView[DataSelection, LinksSelection, MetaSelection]
      ]

    def view[DataSelection, LinksSelection, MetaSelection](
        dataSelection: SelectionBuilder[Comment, DataSelection],
        linksSelection: SelectionBuilder[PaginationLinks, LinksSelection],
        metaSelection: SelectionBuilder[PageMetadata, MetaSelection]
    ): ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      (data(dataSelection) ~ links(linksSelection) ~ meta(metaSelection)).map {
        case ((data, links), meta) => CommentsPageView(data, links, meta)
      }

    def data[A](
        innerSelection: SelectionBuilder[Comment, A]
    ): SelectionBuilder[CommentsPage, Option[List[Option[A]]]] =
      _root_.caliban.client.SelectionBuilder
        .Field("data", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))
    def links[A](
        innerSelection: SelectionBuilder[PaginationLinks, A]
    ): SelectionBuilder[CommentsPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("links", OptionOf(Obj(innerSelection)))
    def meta[A](
        innerSelection: SelectionBuilder[PageMetadata, A]
    ): SelectionBuilder[CommentsPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("meta", OptionOf(Obj(innerSelection)))
  }

  type Company
  object Company {

    final case class CompanyView(
        name: Option[String],
        catchPhrase: Option[String],
        bs: Option[String]
    )

    type ViewSelection = SelectionBuilder[Company, CompanyView]

    def view: ViewSelection = (name ~ catchPhrase ~ bs).map {
      case ((name, catchPhrase), bs) => CompanyView(name, catchPhrase, bs)
    }

    def name: SelectionBuilder[Company, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("name", OptionOf(Scalar()))
    def catchPhrase: SelectionBuilder[Company, Option[String]] =
      _root_.caliban.client.SelectionBuilder
        .Field("catchPhrase", OptionOf(Scalar()))
    def bs: SelectionBuilder[Company, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("bs", OptionOf(Scalar()))
  }

  type Geo
  object Geo {

    final case class GeoView(lat: Option[Double], lng: Option[Double])

    type ViewSelection = SelectionBuilder[Geo, GeoView]

    def view: ViewSelection = (lat ~ lng).map { case (lat, lng) =>
      GeoView(lat, lng)
    }

    def lat: SelectionBuilder[Geo, Option[Double]] =
      _root_.caliban.client.SelectionBuilder.Field("lat", OptionOf(Scalar()))
    def lng: SelectionBuilder[Geo, Option[Double]] =
      _root_.caliban.client.SelectionBuilder.Field("lng", OptionOf(Scalar()))
  }

  type PageLimitPair
  object PageLimitPair {

    final case class PageLimitPairView(page: Option[Int], limit: Option[Int])

    type ViewSelection = SelectionBuilder[PageLimitPair, PageLimitPairView]

    def view: ViewSelection = (page ~ limit).map { case (page, limit) =>
      PageLimitPairView(page, limit)
    }

    def page: SelectionBuilder[PageLimitPair, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("page", OptionOf(Scalar()))
    def limit: SelectionBuilder[PageLimitPair, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("limit", OptionOf(Scalar()))
  }

  type PageMetadata
  object PageMetadata {

    final case class PageMetadataView(totalCount: Option[Int])

    type ViewSelection = SelectionBuilder[PageMetadata, PageMetadataView]

    def view: ViewSelection =
      totalCount.map(totalCount => PageMetadataView(totalCount))

    def totalCount: SelectionBuilder[PageMetadata, Option[Int]] =
      _root_.caliban.client.SelectionBuilder
        .Field("totalCount", OptionOf(Scalar()))
  }

  type PaginationLinks
  object PaginationLinks {

    final case class PaginationLinksView[
        FirstSelection,
        PrevSelection,
        NextSelection,
        LastSelection
    ](
        first: Option[FirstSelection],
        prev: Option[PrevSelection],
        next: Option[NextSelection],
        last: Option[LastSelection]
    )

    type ViewSelection[
        FirstSelection,
        PrevSelection,
        NextSelection,
        LastSelection
    ] = SelectionBuilder[PaginationLinks, PaginationLinksView[
      FirstSelection,
      PrevSelection,
      NextSelection,
      LastSelection
    ]]

    def view[FirstSelection, PrevSelection, NextSelection, LastSelection](
        firstSelection: SelectionBuilder[PageLimitPair, FirstSelection],
        prevSelection: SelectionBuilder[PageLimitPair, PrevSelection],
        nextSelection: SelectionBuilder[PageLimitPair, NextSelection],
        lastSelection: SelectionBuilder[PageLimitPair, LastSelection]
    ): ViewSelection[
      FirstSelection,
      PrevSelection,
      NextSelection,
      LastSelection
    ] = (first(firstSelection) ~ prev(prevSelection) ~ next(
      nextSelection
    ) ~ last(lastSelection)).map { case (((first, prev), next), last) =>
      PaginationLinksView(first, prev, next, last)
    }

    def first[A](
        innerSelection: SelectionBuilder[PageLimitPair, A]
    ): SelectionBuilder[PaginationLinks, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("first", OptionOf(Obj(innerSelection)))
    def prev[A](
        innerSelection: SelectionBuilder[PageLimitPair, A]
    ): SelectionBuilder[PaginationLinks, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("prev", OptionOf(Obj(innerSelection)))
    def next[A](
        innerSelection: SelectionBuilder[PageLimitPair, A]
    ): SelectionBuilder[PaginationLinks, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("next", OptionOf(Obj(innerSelection)))
    def last[A](
        innerSelection: SelectionBuilder[PageLimitPair, A]
    ): SelectionBuilder[PaginationLinks, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("last", OptionOf(Obj(innerSelection)))
  }

  type Photo
  object Photo {

    final case class PhotoView[AlbumSelection](
        id: Option[String],
        title: Option[String],
        url: Option[String],
        thumbnailUrl: Option[String],
        album: Option[AlbumSelection]
    )

    type ViewSelection[AlbumSelection] =
      SelectionBuilder[Photo, PhotoView[AlbumSelection]]

    def view[AlbumSelection](
        albumSelection: SelectionBuilder[Album, AlbumSelection]
    ): ViewSelection[AlbumSelection] =
      (id ~ title ~ url ~ thumbnailUrl ~ album(albumSelection)).map {
        case ((((id, title), url), thumbnailUrl), album) =>
          PhotoView(id, title, url, thumbnailUrl, album)
      }

    def id: SelectionBuilder[Photo, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("id", OptionOf(Scalar()))
    def title: SelectionBuilder[Photo, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("title", OptionOf(Scalar()))
    def url: SelectionBuilder[Photo, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("url", OptionOf(Scalar()))
    def thumbnailUrl: SelectionBuilder[Photo, Option[String]] =
      _root_.caliban.client.SelectionBuilder
        .Field("thumbnailUrl", OptionOf(Scalar()))
    def album[A](
        innerSelection: SelectionBuilder[Album, A]
    ): SelectionBuilder[Photo, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("album", OptionOf(Obj(innerSelection)))
  }

  type PhotosPage
  object PhotosPage {

    final case class PhotosPageView[
        DataSelection,
        LinksSelection,
        MetaSelection
    ](
        data: Option[List[Option[DataSelection]]],
        links: Option[LinksSelection],
        meta: Option[MetaSelection]
    )

    type ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      SelectionBuilder[
        PhotosPage,
        PhotosPageView[DataSelection, LinksSelection, MetaSelection]
      ]

    def view[DataSelection, LinksSelection, MetaSelection](
        dataSelection: SelectionBuilder[Photo, DataSelection],
        linksSelection: SelectionBuilder[PaginationLinks, LinksSelection],
        metaSelection: SelectionBuilder[PageMetadata, MetaSelection]
    ): ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      (data(dataSelection) ~ links(linksSelection) ~ meta(metaSelection)).map {
        case ((data, links), meta) => PhotosPageView(data, links, meta)
      }

    def data[A](
        innerSelection: SelectionBuilder[Photo, A]
    ): SelectionBuilder[PhotosPage, Option[List[Option[A]]]] =
      _root_.caliban.client.SelectionBuilder
        .Field("data", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))
    def links[A](
        innerSelection: SelectionBuilder[PaginationLinks, A]
    ): SelectionBuilder[PhotosPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("links", OptionOf(Obj(innerSelection)))
    def meta[A](
        innerSelection: SelectionBuilder[PageMetadata, A]
    ): SelectionBuilder[PhotosPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("meta", OptionOf(Obj(innerSelection)))
  }

  type Post
  object Post {

    final case class PostView[UserSelection, CommentsSelection](
        id: Option[String],
        title: Option[String],
        body: Option[String],
        user: Option[UserSelection],
        comments: Option[CommentsSelection]
    )

    type ViewSelection[UserSelection, CommentsSelection] =
      SelectionBuilder[Post, PostView[UserSelection, CommentsSelection]]

    def view[UserSelection, CommentsSelection](
        commentsOptions: Option[PageQueryOptions] = None
    )(
        userSelection: SelectionBuilder[User, UserSelection],
        commentsSelection: SelectionBuilder[CommentsPage, CommentsSelection]
    ): ViewSelection[UserSelection, CommentsSelection] =
      (id ~ title ~ body ~ user(userSelection) ~ comments(commentsOptions)(
        commentsSelection
      )).map { case ((((id, title), body), user), comments) =>
        PostView(id, title, body, user, comments)
      }

    def id: SelectionBuilder[Post, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("id", OptionOf(Scalar()))
    def title: SelectionBuilder[Post, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("title", OptionOf(Scalar()))
    def body: SelectionBuilder[Post, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("body", OptionOf(Scalar()))
    def user[A](
        innerSelection: SelectionBuilder[User, A]
    ): SelectionBuilder[Post, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("user", OptionOf(Obj(innerSelection)))
    def comments[A](
        options: Option[PageQueryOptions] = None
    )(innerSelection: SelectionBuilder[CommentsPage, A])(implicit
        encoder0: ArgEncoder[Option[PageQueryOptions]]
    ): SelectionBuilder[Post, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "comments",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("options", options, "PageQueryOptions")(encoder0))
      )
  }

  type PostsPage
  object PostsPage {

    final case class PostsPageView[
        DataSelection,
        LinksSelection,
        MetaSelection
    ](
        data: Option[List[Option[DataSelection]]],
        links: Option[LinksSelection],
        meta: Option[MetaSelection]
    )

    type ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      SelectionBuilder[
        PostsPage,
        PostsPageView[DataSelection, LinksSelection, MetaSelection]
      ]

    def view[DataSelection, LinksSelection, MetaSelection](
        dataSelection: SelectionBuilder[Post, DataSelection],
        linksSelection: SelectionBuilder[PaginationLinks, LinksSelection],
        metaSelection: SelectionBuilder[PageMetadata, MetaSelection]
    ): ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      (data(dataSelection) ~ links(linksSelection) ~ meta(metaSelection)).map {
        case ((data, links), meta) => PostsPageView(data, links, meta)
      }

    def data[A](
        innerSelection: SelectionBuilder[Post, A]
    ): SelectionBuilder[PostsPage, Option[List[Option[A]]]] =
      _root_.caliban.client.SelectionBuilder
        .Field("data", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))
    def links[A](
        innerSelection: SelectionBuilder[PaginationLinks, A]
    ): SelectionBuilder[PostsPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("links", OptionOf(Obj(innerSelection)))
    def meta[A](
        innerSelection: SelectionBuilder[PageMetadata, A]
    ): SelectionBuilder[PostsPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("meta", OptionOf(Obj(innerSelection)))
  }

  type Todo
  object Todo {

    final case class TodoView[UserSelection](
        id: Option[String],
        title: Option[String],
        completed: Option[Boolean],
        user: Option[UserSelection]
    )

    type ViewSelection[UserSelection] =
      SelectionBuilder[Todo, TodoView[UserSelection]]

    def view[UserSelection](
        userSelection: SelectionBuilder[User, UserSelection]
    ): ViewSelection[UserSelection] =
      (id ~ title ~ completed ~ user(userSelection)).map {
        case (((id, title), completed), user) =>
          TodoView(id, title, completed, user)
      }

    def id: SelectionBuilder[Todo, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("id", OptionOf(Scalar()))
    def title: SelectionBuilder[Todo, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("title", OptionOf(Scalar()))
    def completed: SelectionBuilder[Todo, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder
        .Field("completed", OptionOf(Scalar()))
    def user[A](
        innerSelection: SelectionBuilder[User, A]
    ): SelectionBuilder[Todo, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("user", OptionOf(Obj(innerSelection)))
  }

  type TodosPage
  object TodosPage {

    final case class TodosPageView[
        DataSelection,
        LinksSelection,
        MetaSelection
    ](
        data: Option[List[Option[DataSelection]]],
        links: Option[LinksSelection],
        meta: Option[MetaSelection]
    )

    type ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      SelectionBuilder[
        TodosPage,
        TodosPageView[DataSelection, LinksSelection, MetaSelection]
      ]

    def view[DataSelection, LinksSelection, MetaSelection](
        dataSelection: SelectionBuilder[Todo, DataSelection],
        linksSelection: SelectionBuilder[PaginationLinks, LinksSelection],
        metaSelection: SelectionBuilder[PageMetadata, MetaSelection]
    ): ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      (data(dataSelection) ~ links(linksSelection) ~ meta(metaSelection)).map {
        case ((data, links), meta) => TodosPageView(data, links, meta)
      }

    def data[A](
        innerSelection: SelectionBuilder[Todo, A]
    ): SelectionBuilder[TodosPage, Option[List[Option[A]]]] =
      _root_.caliban.client.SelectionBuilder
        .Field("data", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))
    def links[A](
        innerSelection: SelectionBuilder[PaginationLinks, A]
    ): SelectionBuilder[TodosPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("links", OptionOf(Obj(innerSelection)))
    def meta[A](
        innerSelection: SelectionBuilder[PageMetadata, A]
    ): SelectionBuilder[TodosPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("meta", OptionOf(Obj(innerSelection)))
  }

  type User
  object User {

    final case class UserView[
        AddressSelection,
        CompanySelection,
        PostsSelection,
        AlbumsSelection,
        TodosSelection
    ](
        id: Option[String],
        name: Option[String],
        username: Option[String],
        email: Option[String],
        address: Option[AddressSelection],
        phone: Option[String],
        website: Option[String],
        company: Option[CompanySelection],
        posts: Option[PostsSelection],
        albums: Option[AlbumsSelection],
        todos: Option[TodosSelection]
    )

    type ViewSelection[
        AddressSelection,
        CompanySelection,
        PostsSelection,
        AlbumsSelection,
        TodosSelection
    ] = SelectionBuilder[User, UserView[
      AddressSelection,
      CompanySelection,
      PostsSelection,
      AlbumsSelection,
      TodosSelection
    ]]

    def view[
        AddressSelection,
        CompanySelection,
        PostsSelection,
        AlbumsSelection,
        TodosSelection
    ](
        postsOptions: Option[PageQueryOptions] = None,
        albumsOptions: Option[PageQueryOptions] = None,
        todosOptions: Option[PageQueryOptions] = None
    )(
        addressSelection: SelectionBuilder[Address, AddressSelection],
        companySelection: SelectionBuilder[Company, CompanySelection],
        postsSelection: SelectionBuilder[PostsPage, PostsSelection],
        albumsSelection: SelectionBuilder[AlbumsPage, AlbumsSelection],
        todosSelection: SelectionBuilder[TodosPage, TodosSelection]
    ): ViewSelection[
      AddressSelection,
      CompanySelection,
      PostsSelection,
      AlbumsSelection,
      TodosSelection
    ] = (id ~ name ~ username ~ email ~ address(
      addressSelection
    ) ~ phone ~ website ~ company(companySelection) ~ posts(postsOptions)(
      postsSelection
    ) ~ albums(albumsOptions)(albumsSelection) ~ todos(todosOptions)(
      todosSelection
    )).map {
      case (
            (
              (
                (
                  (
                    (((((id, name), username), email), address), phone),
                    website
                  ),
                  company
                ),
                posts
              ),
              albums
            ),
            todos
          ) =>
        UserView(
          id,
          name,
          username,
          email,
          address,
          phone,
          website,
          company,
          posts,
          albums,
          todos
        )
    }

    def id: SelectionBuilder[User, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("id", OptionOf(Scalar()))
    def name: SelectionBuilder[User, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("name", OptionOf(Scalar()))
    def username: SelectionBuilder[User, Option[String]] =
      _root_.caliban.client.SelectionBuilder
        .Field("username", OptionOf(Scalar()))
    def email: SelectionBuilder[User, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("email", OptionOf(Scalar()))
    def address[A](
        innerSelection: SelectionBuilder[Address, A]
    ): SelectionBuilder[User, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("address", OptionOf(Obj(innerSelection)))
    def phone: SelectionBuilder[User, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("phone", OptionOf(Scalar()))
    def website: SelectionBuilder[User, Option[String]] =
      _root_.caliban.client.SelectionBuilder
        .Field("website", OptionOf(Scalar()))
    def company[A](
        innerSelection: SelectionBuilder[Company, A]
    ): SelectionBuilder[User, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("company", OptionOf(Obj(innerSelection)))
    def posts[A](
        options: Option[PageQueryOptions] = None
    )(innerSelection: SelectionBuilder[PostsPage, A])(implicit
        encoder0: ArgEncoder[Option[PageQueryOptions]]
    ): SelectionBuilder[User, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "posts",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("options", options, "PageQueryOptions")(encoder0))
      )
    def albums[A](
        options: Option[PageQueryOptions] = None
    )(innerSelection: SelectionBuilder[AlbumsPage, A])(implicit
        encoder0: ArgEncoder[Option[PageQueryOptions]]
    ): SelectionBuilder[User, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "albums",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("options", options, "PageQueryOptions")(encoder0))
      )
    def todos[A](
        options: Option[PageQueryOptions] = None
    )(innerSelection: SelectionBuilder[TodosPage, A])(implicit
        encoder0: ArgEncoder[Option[PageQueryOptions]]
    ): SelectionBuilder[User, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "todos",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("options", options, "PageQueryOptions")(encoder0))
      )
  }

  type UsersPage
  object UsersPage {

    final case class UsersPageView[
        DataSelection,
        LinksSelection,
        MetaSelection
    ](
        data: Option[List[Option[DataSelection]]],
        links: Option[LinksSelection],
        meta: Option[MetaSelection]
    )

    type ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      SelectionBuilder[
        UsersPage,
        UsersPageView[DataSelection, LinksSelection, MetaSelection]
      ]

    def view[DataSelection, LinksSelection, MetaSelection](
        dataSelection: SelectionBuilder[User, DataSelection],
        linksSelection: SelectionBuilder[PaginationLinks, LinksSelection],
        metaSelection: SelectionBuilder[PageMetadata, MetaSelection]
    ): ViewSelection[DataSelection, LinksSelection, MetaSelection] =
      (data(dataSelection) ~ links(linksSelection) ~ meta(metaSelection)).map {
        case ((data, links), meta) => UsersPageView(data, links, meta)
      }

    def data[A](
        innerSelection: SelectionBuilder[User, A]
    ): SelectionBuilder[UsersPage, Option[List[Option[A]]]] =
      _root_.caliban.client.SelectionBuilder
        .Field("data", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))
    def links[A](
        innerSelection: SelectionBuilder[PaginationLinks, A]
    ): SelectionBuilder[UsersPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("links", OptionOf(Obj(innerSelection)))
    def meta[A](
        innerSelection: SelectionBuilder[PageMetadata, A]
    ): SelectionBuilder[UsersPage, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("meta", OptionOf(Obj(innerSelection)))
  }

  final case class AddressInput(
      street: Option[String] = None,
      suite: Option[String] = None,
      city: Option[String] = None,
      zipcode: Option[String] = None,
      geo: Option[GeoInput] = None
  )
  object AddressInput {
    implicit val encoder: ArgEncoder[AddressInput] =
      new ArgEncoder[AddressInput] {
        override def encode(value: AddressInput): __Value =
          __ObjectValue(
            List(
              "street" -> value.street.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "suite" -> value.suite.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "city" -> value.city.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "zipcode" -> value.zipcode.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "geo" -> value.geo.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[GeoInput]].encode(value)
              )
            )
          )
      }
  }
  final case class CompanyInput(
      name: Option[String] = None,
      catchPhrase: Option[String] = None,
      bs: Option[String] = None
  )
  object CompanyInput {
    implicit val encoder: ArgEncoder[CompanyInput] =
      new ArgEncoder[CompanyInput] {
        override def encode(value: CompanyInput): __Value =
          __ObjectValue(
            List(
              "name" -> value.name.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "catchPhrase" -> value.catchPhrase.fold(__NullValue: __Value)(
                value => implicitly[ArgEncoder[String]].encode(value)
              ),
              "bs" -> value.bs.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              )
            )
          )
      }
  }
  final case class CreateAlbumInput(title: String, userId: String)
  object CreateAlbumInput {
    implicit val encoder: ArgEncoder[CreateAlbumInput] =
      new ArgEncoder[CreateAlbumInput] {
        override def encode(value: CreateAlbumInput): __Value =
          __ObjectValue(
            List(
              "title" -> implicitly[ArgEncoder[String]].encode(value.title),
              "userId" -> implicitly[ArgEncoder[String]].encode(value.userId)
            )
          )
      }
  }
  final case class CreateCommentInput(name: String, email: String, body: String)
  object CreateCommentInput {
    implicit val encoder: ArgEncoder[CreateCommentInput] =
      new ArgEncoder[CreateCommentInput] {
        override def encode(value: CreateCommentInput): __Value =
          __ObjectValue(
            List(
              "name" -> implicitly[ArgEncoder[String]].encode(value.name),
              "email" -> implicitly[ArgEncoder[String]].encode(value.email),
              "body" -> implicitly[ArgEncoder[String]].encode(value.body)
            )
          )
      }
  }
  final case class CreatePhotoInput(
      title: String,
      url: String,
      thumbnailUrl: String
  )
  object CreatePhotoInput {
    implicit val encoder: ArgEncoder[CreatePhotoInput] =
      new ArgEncoder[CreatePhotoInput] {
        override def encode(value: CreatePhotoInput): __Value =
          __ObjectValue(
            List(
              "title" -> implicitly[ArgEncoder[String]].encode(value.title),
              "url" -> implicitly[ArgEncoder[String]].encode(value.url),
              "thumbnailUrl" -> implicitly[ArgEncoder[String]].encode(
                value.thumbnailUrl
              )
            )
          )
      }
  }
  final case class CreatePostInput(title: String, body: String)
  object CreatePostInput {
    implicit val encoder: ArgEncoder[CreatePostInput] =
      new ArgEncoder[CreatePostInput] {
        override def encode(value: CreatePostInput): __Value =
          __ObjectValue(
            List(
              "title" -> implicitly[ArgEncoder[String]].encode(value.title),
              "body" -> implicitly[ArgEncoder[String]].encode(value.body)
            )
          )
      }
  }
  final case class CreateTodoInput(title: String, completed: Boolean)
  object CreateTodoInput {
    implicit val encoder: ArgEncoder[CreateTodoInput] =
      new ArgEncoder[CreateTodoInput] {
        override def encode(value: CreateTodoInput): __Value =
          __ObjectValue(
            List(
              "title" -> implicitly[ArgEncoder[String]].encode(value.title),
              "completed" -> implicitly[ArgEncoder[Boolean]].encode(
                value.completed
              )
            )
          )
      }
  }
  final case class CreateUserInput(
      name: String,
      username: String,
      email: String,
      address: Option[AddressInput] = None,
      phone: Option[String] = None,
      website: Option[String] = None,
      company: Option[CompanyInput] = None
  )
  object CreateUserInput {
    implicit val encoder: ArgEncoder[CreateUserInput] =
      new ArgEncoder[CreateUserInput] {
        override def encode(value: CreateUserInput): __Value =
          __ObjectValue(
            List(
              "name" -> implicitly[ArgEncoder[String]].encode(value.name),
              "username" -> implicitly[ArgEncoder[String]]
                .encode(value.username),
              "email" -> implicitly[ArgEncoder[String]].encode(value.email),
              "address" -> value.address.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[AddressInput]].encode(value)
              ),
              "phone" -> value.phone.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "website" -> value.website.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "company" -> value.company.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[CompanyInput]].encode(value)
              )
            )
          )
      }
  }
  final case class GeoInput(
      lat: Option[Double] = None,
      lng: Option[Double] = None
  )
  object GeoInput {
    implicit val encoder: ArgEncoder[GeoInput] = new ArgEncoder[GeoInput] {
      override def encode(value: GeoInput): __Value =
        __ObjectValue(
          List(
            "lat" -> value.lat.fold(__NullValue: __Value)(value =>
              implicitly[ArgEncoder[Double]].encode(value)
            ),
            "lng" -> value.lng.fold(__NullValue: __Value)(value =>
              implicitly[ArgEncoder[Double]].encode(value)
            )
          )
        )
    }
  }
  final case class OperatorOptions(
      kind: Option[OperatorKindEnum] = None,
      field: Option[String] = None,
      value: Option[String] = None
  )
  object OperatorOptions {
    implicit val encoder: ArgEncoder[OperatorOptions] =
      new ArgEncoder[OperatorOptions] {
        override def encode(value: OperatorOptions): __Value =
          __ObjectValue(
            List(
              "kind" -> value.kind.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[OperatorKindEnum]].encode(value)
              ),
              "field" -> value.field.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "value" -> value.value.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              )
            )
          )
      }
  }
  final case class PageQueryOptions(
      paginate: Option[PaginateOptions] = None,
      slice: Option[SliceOptions] = None,
      sort: Option[List[Option[SortOptions]]] = None,
      operators: Option[List[Option[OperatorOptions]]] = None,
      search: Option[SearchOptions] = None
  )
  object PageQueryOptions {
    implicit val encoder: ArgEncoder[PageQueryOptions] =
      new ArgEncoder[PageQueryOptions] {
        override def encode(value: PageQueryOptions): __Value =
          __ObjectValue(
            List(
              "paginate" -> value.paginate.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[PaginateOptions]].encode(value)
              ),
              "slice" -> value.slice.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[SliceOptions]].encode(value)
              ),
              "sort" -> value.sort.fold(__NullValue: __Value)(value =>
                __ListValue(
                  value.map(value =>
                    value.fold(__NullValue: __Value)(value =>
                      implicitly[ArgEncoder[SortOptions]].encode(value)
                    )
                  )
                )
              ),
              "operators" -> value.operators.fold(__NullValue: __Value)(value =>
                __ListValue(
                  value.map(value =>
                    value.fold(__NullValue: __Value)(value =>
                      implicitly[ArgEncoder[OperatorOptions]].encode(value)
                    )
                  )
                )
              ),
              "search" -> value.search.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[SearchOptions]].encode(value)
              )
            )
          )
      }
  }
  final case class PaginateOptions(
      page: Option[Int] = None,
      limit: Option[Int] = None
  )
  object PaginateOptions {
    implicit val encoder: ArgEncoder[PaginateOptions] =
      new ArgEncoder[PaginateOptions] {
        override def encode(value: PaginateOptions): __Value =
          __ObjectValue(
            List(
              "page" -> value.page.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[Int]].encode(value)
              ),
              "limit" -> value.limit.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[Int]].encode(value)
              )
            )
          )
      }
  }
  final case class SearchOptions(q: Option[String] = None)
  object SearchOptions {
    implicit val encoder: ArgEncoder[SearchOptions] =
      new ArgEncoder[SearchOptions] {
        override def encode(value: SearchOptions): __Value =
          __ObjectValue(
            List(
              "q" -> value.q.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              )
            )
          )
      }
  }
  final case class SliceOptions(
      start: Option[Int] = None,
      end: Option[Int] = None,
      limit: Option[Int] = None
  )
  object SliceOptions {
    implicit val encoder: ArgEncoder[SliceOptions] =
      new ArgEncoder[SliceOptions] {
        override def encode(value: SliceOptions): __Value =
          __ObjectValue(
            List(
              "start" -> value.start.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[Int]].encode(value)
              ),
              "end" -> value.end.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[Int]].encode(value)
              ),
              "limit" -> value.limit.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[Int]].encode(value)
              )
            )
          )
      }
  }
  final case class SortOptions(
      field: Option[String] = None,
      order: Option[SortOrderEnum] = None
  )
  object SortOptions {
    implicit val encoder: ArgEncoder[SortOptions] =
      new ArgEncoder[SortOptions] {
        override def encode(value: SortOptions): __Value =
          __ObjectValue(
            List(
              "field" -> value.field.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "order" -> value.order.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[SortOrderEnum]].encode(value)
              )
            )
          )
      }
  }
  final case class UpdateAlbumInput(
      title: Option[String] = None,
      userId: Option[String] = None
  )
  object UpdateAlbumInput {
    implicit val encoder: ArgEncoder[UpdateAlbumInput] =
      new ArgEncoder[UpdateAlbumInput] {
        override def encode(value: UpdateAlbumInput): __Value =
          __ObjectValue(
            List(
              "title" -> value.title.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "userId" -> value.userId.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              )
            )
          )
      }
  }
  final case class UpdateCommentInput(
      name: Option[String] = None,
      email: Option[String] = None,
      body: Option[String] = None
  )
  object UpdateCommentInput {
    implicit val encoder: ArgEncoder[UpdateCommentInput] =
      new ArgEncoder[UpdateCommentInput] {
        override def encode(value: UpdateCommentInput): __Value =
          __ObjectValue(
            List(
              "name" -> value.name.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "email" -> value.email.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "body" -> value.body.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              )
            )
          )
      }
  }
  final case class UpdatePhotoInput(
      title: Option[String] = None,
      url: Option[String] = None,
      thumbnailUrl: Option[String] = None
  )
  object UpdatePhotoInput {
    implicit val encoder: ArgEncoder[UpdatePhotoInput] =
      new ArgEncoder[UpdatePhotoInput] {
        override def encode(value: UpdatePhotoInput): __Value =
          __ObjectValue(
            List(
              "title" -> value.title.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "url" -> value.url.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "thumbnailUrl" -> value.thumbnailUrl.fold(__NullValue: __Value)(
                value => implicitly[ArgEncoder[String]].encode(value)
              )
            )
          )
      }
  }
  final case class UpdatePostInput(
      title: Option[String] = None,
      body: Option[String] = None
  )
  object UpdatePostInput {
    implicit val encoder: ArgEncoder[UpdatePostInput] =
      new ArgEncoder[UpdatePostInput] {
        override def encode(value: UpdatePostInput): __Value =
          __ObjectValue(
            List(
              "title" -> value.title.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "body" -> value.body.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              )
            )
          )
      }
  }
  final case class UpdateTodoInput(
      title: Option[String] = None,
      completed: Option[Boolean] = None
  )
  object UpdateTodoInput {
    implicit val encoder: ArgEncoder[UpdateTodoInput] =
      new ArgEncoder[UpdateTodoInput] {
        override def encode(value: UpdateTodoInput): __Value =
          __ObjectValue(
            List(
              "title" -> value.title.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "completed" -> value.completed.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[Boolean]].encode(value)
              )
            )
          )
      }
  }
  final case class UpdateUserInput(
      name: Option[String] = None,
      username: Option[String] = None,
      email: Option[String] = None,
      address: Option[AddressInput] = None,
      phone: Option[String] = None,
      website: Option[String] = None,
      company: Option[CompanyInput] = None
  )
  object UpdateUserInput {
    implicit val encoder: ArgEncoder[UpdateUserInput] =
      new ArgEncoder[UpdateUserInput] {
        override def encode(value: UpdateUserInput): __Value =
          __ObjectValue(
            List(
              "name" -> value.name.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "username" -> value.username.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "email" -> value.email.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "address" -> value.address.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[AddressInput]].encode(value)
              ),
              "phone" -> value.phone.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "website" -> value.website.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[String]].encode(value)
              ),
              "company" -> value.company.fold(__NullValue: __Value)(value =>
                implicitly[ArgEncoder[CompanyInput]].encode(value)
              )
            )
          )
      }
  }
  type Query = _root_.caliban.client.Operations.RootQuery
  object Query {
    def albums[A](
        options: Option[PageQueryOptions] = None
    )(innerSelection: SelectionBuilder[AlbumsPage, A])(implicit
        encoder0: ArgEncoder[Option[PageQueryOptions]]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "albums",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("options", options, "PageQueryOptions")(encoder0))
      )
    def album[A](id: String)(innerSelection: SelectionBuilder[Album, A])(
        implicit encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "album",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("id", id, "ID!")(encoder0))
      )
    def comments[A](
        options: Option[PageQueryOptions] = None
    )(innerSelection: SelectionBuilder[CommentsPage, A])(implicit
        encoder0: ArgEncoder[Option[PageQueryOptions]]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "comments",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("options", options, "PageQueryOptions")(encoder0))
      )
    def comment[A](id: String)(innerSelection: SelectionBuilder[Comment, A])(
        implicit encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "comment",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("id", id, "ID!")(encoder0))
      )
    def photos[A](
        options: Option[PageQueryOptions] = None
    )(innerSelection: SelectionBuilder[PhotosPage, A])(implicit
        encoder0: ArgEncoder[Option[PageQueryOptions]]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "photos",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("options", options, "PageQueryOptions")(encoder0))
      )
    def photo[A](id: String)(innerSelection: SelectionBuilder[Photo, A])(
        implicit encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "photo",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("id", id, "ID!")(encoder0))
      )
    def posts[A](
        options: Option[PageQueryOptions] = None
    )(innerSelection: SelectionBuilder[PostsPage, A])(implicit
        encoder0: ArgEncoder[Option[PageQueryOptions]]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "posts",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("options", options, "PageQueryOptions")(encoder0))
      )
    def post[A](id: String)(innerSelection: SelectionBuilder[Post, A])(implicit
        encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "post",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("id", id, "ID!")(encoder0))
      )
    def todos[A](
        options: Option[PageQueryOptions] = None
    )(innerSelection: SelectionBuilder[TodosPage, A])(implicit
        encoder0: ArgEncoder[Option[PageQueryOptions]]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "todos",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("options", options, "PageQueryOptions")(encoder0))
      )
    def todo[A](id: String)(innerSelection: SelectionBuilder[Todo, A])(implicit
        encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "todo",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("id", id, "ID!")(encoder0))
      )
    def users[A](
        options: Option[PageQueryOptions] = None
    )(innerSelection: SelectionBuilder[UsersPage, A])(implicit
        encoder0: ArgEncoder[Option[PageQueryOptions]]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "users",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("options", options, "PageQueryOptions")(encoder0))
      )
    def user[A](id: String)(innerSelection: SelectionBuilder[User, A])(implicit
        encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "user",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("id", id, "ID!")(encoder0))
      )
  }

  type Mutation = _root_.caliban.client.Operations.RootMutation
  object Mutation {
    def createAlbum[A](
        input: CreateAlbumInput
    )(innerSelection: SelectionBuilder[Album, A])(implicit
        encoder0: ArgEncoder[CreateAlbumInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "createAlbum",
      OptionOf(Obj(innerSelection)),
      arguments = List(Argument("input", input, "CreateAlbumInput!")(encoder0))
    )
    def updateAlbum[A](id: String, input: UpdateAlbumInput)(
        innerSelection: SelectionBuilder[Album, A]
    )(implicit
        encoder0: ArgEncoder[String],
        encoder1: ArgEncoder[UpdateAlbumInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "updateAlbum",
      OptionOf(Obj(innerSelection)),
      arguments = List(
        Argument("id", id, "ID!")(encoder0),
        Argument("input", input, "UpdateAlbumInput!")(encoder1)
      )
    )
    def deleteAlbum(id: String)(implicit
        encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      Boolean
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "deleteAlbum",
      OptionOf(Scalar()),
      arguments = List(Argument("id", id, "ID!")(encoder0))
    )
    def createComment[A](
        input: CreateCommentInput
    )(innerSelection: SelectionBuilder[Comment, A])(implicit
        encoder0: ArgEncoder[CreateCommentInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "createComment",
      OptionOf(Obj(innerSelection)),
      arguments =
        List(Argument("input", input, "CreateCommentInput!")(encoder0))
    )
    def updateComment[A](id: String, input: UpdateCommentInput)(
        innerSelection: SelectionBuilder[Comment, A]
    )(implicit
        encoder0: ArgEncoder[String],
        encoder1: ArgEncoder[UpdateCommentInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "updateComment",
      OptionOf(Obj(innerSelection)),
      arguments = List(
        Argument("id", id, "ID!")(encoder0),
        Argument("input", input, "UpdateCommentInput!")(encoder1)
      )
    )
    def deleteComment(id: String)(implicit
        encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      Boolean
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "deleteComment",
      OptionOf(Scalar()),
      arguments = List(Argument("id", id, "ID!")(encoder0))
    )
    def createPhoto[A](
        input: CreatePhotoInput
    )(innerSelection: SelectionBuilder[Photo, A])(implicit
        encoder0: ArgEncoder[CreatePhotoInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "createPhoto",
      OptionOf(Obj(innerSelection)),
      arguments = List(Argument("input", input, "CreatePhotoInput!")(encoder0))
    )
    def updatePhoto[A](id: String, input: UpdatePhotoInput)(
        innerSelection: SelectionBuilder[Photo, A]
    )(implicit
        encoder0: ArgEncoder[String],
        encoder1: ArgEncoder[UpdatePhotoInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "updatePhoto",
      OptionOf(Obj(innerSelection)),
      arguments = List(
        Argument("id", id, "ID!")(encoder0),
        Argument("input", input, "UpdatePhotoInput!")(encoder1)
      )
    )
    def deletePhoto(id: String)(implicit
        encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      Boolean
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "deletePhoto",
      OptionOf(Scalar()),
      arguments = List(Argument("id", id, "ID!")(encoder0))
    )
    def createPost[A](
        input: CreatePostInput
    )(innerSelection: SelectionBuilder[Post, A])(implicit
        encoder0: ArgEncoder[CreatePostInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "createPost",
      OptionOf(Obj(innerSelection)),
      arguments = List(Argument("input", input, "CreatePostInput!")(encoder0))
    )
    def updatePost[A](id: String, input: UpdatePostInput)(
        innerSelection: SelectionBuilder[Post, A]
    )(implicit
        encoder0: ArgEncoder[String],
        encoder1: ArgEncoder[UpdatePostInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "updatePost",
      OptionOf(Obj(innerSelection)),
      arguments = List(
        Argument("id", id, "ID!")(encoder0),
        Argument("input", input, "UpdatePostInput!")(encoder1)
      )
    )
    def deletePost(id: String)(implicit
        encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      Boolean
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "deletePost",
      OptionOf(Scalar()),
      arguments = List(Argument("id", id, "ID!")(encoder0))
    )
    def createTodo[A](
        input: CreateTodoInput
    )(innerSelection: SelectionBuilder[Todo, A])(implicit
        encoder0: ArgEncoder[CreateTodoInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "createTodo",
      OptionOf(Obj(innerSelection)),
      arguments = List(Argument("input", input, "CreateTodoInput!")(encoder0))
    )
    def updateTodo[A](id: String, input: UpdateTodoInput)(
        innerSelection: SelectionBuilder[Todo, A]
    )(implicit
        encoder0: ArgEncoder[String],
        encoder1: ArgEncoder[UpdateTodoInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "updateTodo",
      OptionOf(Obj(innerSelection)),
      arguments = List(
        Argument("id", id, "ID!")(encoder0),
        Argument("input", input, "UpdateTodoInput!")(encoder1)
      )
    )
    def deleteTodo(id: String)(implicit
        encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      Boolean
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "deleteTodo",
      OptionOf(Scalar()),
      arguments = List(Argument("id", id, "ID!")(encoder0))
    )
    def createUser[A](
        input: CreateUserInput
    )(innerSelection: SelectionBuilder[User, A])(implicit
        encoder0: ArgEncoder[CreateUserInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "createUser",
      OptionOf(Obj(innerSelection)),
      arguments = List(Argument("input", input, "CreateUserInput!")(encoder0))
    )
    def updateUser[A](id: String, input: UpdateUserInput)(
        innerSelection: SelectionBuilder[User, A]
    )(implicit
        encoder0: ArgEncoder[String],
        encoder1: ArgEncoder[UpdateUserInput]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      A
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "updateUser",
      OptionOf(Obj(innerSelection)),
      arguments = List(
        Argument("id", id, "ID!")(encoder0),
        Argument("input", input, "UpdateUserInput!")(encoder1)
      )
    )
    def deleteUser(id: String)(implicit
        encoder0: ArgEncoder[String]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootMutation, Option[
      Boolean
    ]] = _root_.caliban.client.SelectionBuilder.Field(
      "deleteUser",
      OptionOf(Scalar()),
      arguments = List(Argument("id", id, "ID!")(encoder0))
    )
  }

}
