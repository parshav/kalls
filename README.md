# Kalls

Kalls provides an easy way to define and build API's in Kotlin using DSL's, this sample project utilizes Kalls in and Android app, consuming
a few of the available SpaceX launch Api's.

(Note this is a WIP, currently not exposed as a library, thus missing plenty of features)

## How it works

There are two parts to creating a consumable API in Kalls.

- Building the DSL
- Functions consuming the DSL and

The DSL defines the API, the function is exposed to consume this.

In this project, the sample DSL consuming the SpaceX API's are under the `networking` module, within the `Networking.kt` file.

Building the DSL includes defining the base-url's endpoint

```
    private val api = kalls("https://api.spacexdata.com/v3") {

    }
```

You only need the endpoint path, and the return model type to add a new endpoint. From the SpaceX API's, the `/roadster`
endpoint can be added in as easy as :

```
    private val api = kalls("https://api.spacexdata.com/v3") {

        "/roadster" <RoadsterModel> {

        }
    }

```

This DSL so far defines an endpoint `/roadster` and a model data class `RoadsterModel`, the class being mapped to the endpoint by kalls.
Now, to consume the API, you only need to provide a function and use the `makeKall` function from kalls.

```
fun roadster(callback: Kallback<RoadsterModel>) = api.makeKall(callback = callback)
```

`Kallback` is a typealias provided by kalls which is defined as `(Either<String, T>) -> Unit`. Currently, a `String` is propagated up
as the Error.

Finally, an example of this function being consumed :

```
val networking = Networking

networking.roadster {
            it.fold(
                    {
                        Log.d("pv", "Error in Roadster $it")
                    },
                    {
                        Log.d("pv", "RoadsterModel Returned")
                    }
            )
        }

```

The `it` provided by the lamda is the callback from Kalls, containing either the success of parsed model response, or
an error String explaining what the error could be.

A parameter is defined in the endpoint within the `{}` brackets, and parameter-values passed as a `vararg` of `Pair` in the
`makeKall` function.

The `/history` endpoint is an example of this, where the endpoint and parameters are defined
in the DSL as

```
"/history/{n}"<HistoryModel> {

        parameters["n"] = "1"   // default
}
```

Now the provided function can be a little dynamic based on the parameter

```
fun historyFor(number: Int, callback: Kallback<HistoryModel>) = api.makeKall(
                                                                    params = *arrayOf("n" pairWith number.toString()),
                                                                    callback = callback
                                                                )
```

Finally, the `/launches` endpoint is an example of nested endpoints. `/launches` in the sample project can lead to either
`/latest` for the latest launch, or `/next` for the upcoming launch.

Although this can be done as a parameter, eg `/launches/{type}` and replaceing type with the required parameter,
you can write nested endpoints in DSL using kalls.

```
"/launches"<None> {

        "/latest"<LatestLaunchModel> {

        } referAs "latest"

        "/next"<NextLaunchModel> {

        } referAs "next"

    } referAs "launches"

```

Since the endpoint requires a class, a provided `None` object can be used. Also, since `LatestLaunchModel` and `NextLaunchModel` are
typealias's to the same data class, we use an infix function `referAs` that maps the endpoint, class, to a string.

To consume this API, you can make use of the `makeKallGroup` function and segregate
the functions per endpoint :

```

fun latestLaunch(callback: Kallback<LatestLaunchModel>) =

            api.makeKallGroup("launches", "latest", callback)

```


```
fun latestLaunch(callback: Kallback<NextLaunchModel>) =

            api.makeKallGroup("launches", "next", callback)
```

Clashing models for single endpoints too can use the `referAs` function to be mapped to as a String, and consumed using the `ref` parameter in the `makeKall`
function.

## Full Example

Here is the complete example of a couple endpoints from SpaceX API :

```

    private val api = kall ("https://api.spacexdata.com/v3) {

        "/roadster"<RoadsterModel> {

        }

        "/history/{n}"<HistoryModel> {

                parameters["n"] = "1" // default

        } referAs "dynamic history"


        "/launches"<None> {

            "/latest"<LatestLaunchModel> {

            } referAs "latest"

            "/next"<NextLaunchModel> {

            } referAs "next"

        } referAs "launches"
    }

    fun latestLaunch(callback: Kallback<LatestLaunchModel>) =
                api.makeKallGroup("launches", "latest", callback)

    fun historyFor(number: Int, callback: Kallback<HistoryModel>) =
            api.makeKall(
                    params = *arrayOf("n" pairWith number.toString()),
                    callback = callback
            )

    fun roadster(callback: Kallback<RoadsterModel>) =
            api.makeKall(callback = callback)
```

You can keep the `kalls` object private as that is used to Define the API structure, whereas the functions public to consume them.

## Supporting Libraries

Kalls uses the following libraries to manage API calls

 - Fuel
 - Klaxon
 - Arrow

 Fuel is the primary library used for the network requests. The DSL builds a definition of the API, the provided
  function `makeKall` and `makeKallGroup` used for making network requests use Fuel.

Klaxon is used for parsing the response to the provided model.

The only part of Arrow currently used is the `Either<L, R>` return type, enabling an easy way to propagate messages
from the API layer to where the call is eventually returned to.

## Contribute

I've currently added issues for missing features that should get in, feel free to create any more you'd like to see.