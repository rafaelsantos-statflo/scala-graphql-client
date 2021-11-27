Project creation
================

This project was created with:
```bash
sbt new scala/scala3.g8
```

GraphQL Schema
==============
We are going query [GraphqlZero](https://graphqlzero.almansi.me) which is a free, online GraphQL API
we can use to get fake data.

The GraphQL schema in this project was downloaded from [GraphqlZero API]
and saved as: `src/main/graphql/graphqlzero.graphql`

GraphQL Client
--------------
This project is configured with [Caliban GraphQL Client](https://ghostdogpr.github.io/caliban/docs/client.html).

### Dependencies
The `build.sbt` contains the necessary dependencies:
```
"com.github.ghostdogpr" %% "caliban-client" % "1.2.1"
```

The `build.sbt` also enables the `CalibanPlugin`:
```
.enablePlugins(CalibanPlugin)
```

The `project/plugins.sbt` contains the `CalibanPlugin`:
```
addSbtPlugin("com.github.ghostdogpr" % "caliban-codegen-sbt" % "1.3.0")
```

### Code Generation
`CalibanPlugin` allows you to generate the client code from a schema file or from a server URL.

Run this `sbt` command to translate `src/main/graphql/graphqlzero.graphql` into a Caliban-generated client library
saved in `src/main/scala/graphqlzero/Client.scala`:
```sbt
calibanGenClient src/main/graphql/graphqlzero.graphql src/main/scala/graphqlzero/Client.scala --genView true
```

Running the Application
-----------------------

The `run` sbt command is going to run the main definition from `src/main/scala/Main.scala`:
```sbt
run
```

The application is going to output a simple query equivalent to the following GraphQL query against the:

```graphql
query {
    album(id: 1) {
        id
        title
    }
}
```

[GraphqlZero API]: https://graphqlzero.almansi.me/api