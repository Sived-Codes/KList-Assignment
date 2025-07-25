KList DSL for Jetpack Compose
KList is a declarative, fluent, and reusable Domain-Specific Language (DSL) for building list-based user interfaces in Jetpack Compose. Inspired by the Modifier chaining pattern, it simplifies creating customizable lists with headers, items, animations, and dividers, while maintaining a clean and scalable codebase.
Features

Fluent API: Chain methods like padding(), header(), items(), clickable(), withDividers(), and animated() for intuitive configuration.
Lazy Rendering: Uses LazyColumn for efficient rendering of large datasets.
Customizable: Supports headers, dividers, animations, and custom item content.
Type-Safe: Generic support for any data type in list items.
Material Design: Adheres to Google's Material Design guidelines for consistent UI.

Installation
To include KList in your project, add it as a dependency via JitPack.
Step 1: Add the JitPack Repository
Add the JitPack repository to your root settings.gradle file at the end of the repositories block:
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

Step 2: Add the Dependency
Add the KList dependency to your app-level build.gradle file:
dependencies {
    implementation 'com.github.prashant:klist-dsl:1.0.0'
}

Step 3: Enable Jetpack Compose
Ensure Jetpack Compose is enabled in your app-level build.gradle file:
android {
    buildFeatures {
        compose true
    }
    composeOptions {
        kotlinCompilerExtensionVersion '1.7.0'
    }
}

dependencies {
    implementation platform('androidx.compose:compose-bom:2024.10.00')
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.material3:material3'
    implementation 'androidx.compose.runtime:runtime'
    implementation 'androidx.compose.foundation:foundation'
}

Step 4: Sync Project
Sync your project with Gradle to download the KList library and its dependencies.
Usage
KList provides a fluent API to configure and render lists. Below are examples demonstrating various use cases.
Basic List
Create a simple list with a header and items:
@Composable
fun BasicListDemo() {
    val items = listOf("Bitcoin", "Ethereum", "Ripple")
    KList.create
        .padding(16.dp)
        .header("Top Coins")
        .items(items) { item ->
            Text(
                text = item,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }
        .render()
}

List with Custom Items
Use the provided KListItem component for a cryptocurrency list:
data class Coin(val name: String, val price: Double)

@Composable
fun CoinListDemo() {
    val coins = listOf(
        Coin("Bitcoin", 45000.0),
        Coin("Ethereum", 3000.0)
    )
    KList.create
        .padding(16.dp)
        .header("Market Leaders")
        .items(coins) { coin ->
            KListItem(
                coin = Coin(
                    uid = coin.name.hashCode().toLong(),
                    name = coin.name,
                    symbol = coin.name.take(3).uppercase(),
                    price = coin.price,
                    changePercent = 0.0,
                    volume = 0L,
                    marketCap = 0L
                )
            )
        }
        .render()
}

Multiple Sections
Create a list with multiple headers and sections:
@Composable
fun MultiSectionListDemo() {
    val topGainers = listOf("Bitcoin", "Cardano")
    val topLosers = listOf("Dogecoin", "Shiba Inu")
    KList.create
        .padding(16.dp)
        .header("Top Gainers", topGainers) { item ->
            Text(item, modifier = Modifier.padding(8.dp))
        }
        .header("Top Losers", topLosers) { item ->
            Text(item, modifier = Modifier.padding(8.dp))
        }
        .withDividers()
        .render()
}

Animated List
Add animations to item appearances:
@Composable
fun AnimatedListDemo() {
    val coins = listOf("Bitcoin", "Ethereum", "Litecoin")
    KList.create
        .padding(16.dp)
        .header("Animated Coins")
        .items(coins) { item ->
            Text(item, modifier = Modifier.padding(8.dp))
        }
        .animated()
        .withDividers()
        .render()
}

Clickable List
Add click handlers to individual items or the entire list:
@Composable
fun ClickableListDemo() {
    val coins = listOf("Bitcoin", "Ethereum")
    KList.create
        .padding(16.dp)
        .header("Clickable Coins")
        .items(coins) { coin ->
            KListItem(
                coin = Coin(
                    uid = coin.hashCode().toLong(),
                    name = coin,
                    symbol = coin.take(3).uppercase(),
                    price = 0.0,
                    changePercent = 0.0,
                    volume = 0L,
                    marketCap = 0L
                ),
                onClick = { /* Handle click */ }
            )
        }
        .clickable { /* Handle list click */ }
        .render()
}

Integration with ViewModel
Use KList with a ViewModel for dynamic data:
@Composable
fun DynamicListDemo(viewModel: KListDemoViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isLoading) {
        CircularProgressIndicator()
    } else if (uiState.error != null) {
        Text(
            text = "Error: ${uiState.error}",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        KList.create
            .padding(16.dp)
            .header("Dynamic Coins")
            .items(uiState.basicDemoCoins) { coin ->
                KListItem(coin) { viewModel.onCoinClick(coin) }
            }
            .animated()
            .withDividers()
            .render()
    }
}

Project Structure

Core: KList.kt, KListConfig.kt, KListSection.kt - Core DSL and configuration logic.
Utils: KListConstants.kt, KListExtensions.kt, KListTheme.kt - Constants, extensions, and theme utilities.
View Components: KListHeader.kt, KListItem.kt - Reusable UI components for headers and items.

Sample KListDemo.kt
Below is a sample KListDemo.kt to demonstrate different configurations:
@Composable
fun KListDemo(viewModel: KListDemoViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    Column {
        when (uiState.selectedDemo) {
            DemoType.BASIC -> {
                KList.create
                    .padding(16.dp)
                    .header("Basic Demo")
                    .items(uiState.basicDemoCoins) { coin ->
                        KListItem(coin) { viewModel.onCoinClick(coin) }
                    }
                    .render()
            }
            DemoType.ADVANCED -> {
                KList.create
                    .padding(16.dp)
                    .header("Top Gainers", uiState.basicDemoCoins) { coin ->
                        KListItem(coin) { viewModel.onCoinClick(coin) }
                    }
                    .header("All Coins", uiState.advancedDemoCoins) { coin ->
                        KListItem(coin) { viewModel.onCoinClick(coin) }
                    }
                    .withDividers()
                    .render()
            }
            DemoType.ANIMATED -> {
                KList.create
                    .padding(16.dp)
                    .header("Animated Demo")
                    .items(uiState.animatedDemoCoins) { coin ->
                        KListItem(coin) { viewModel.onCoinClick(coin) }
                    }
                    .animated()
                    .withDividers()
                    .render()
            }
            null -> {
                Text("Select a demo type", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

Known Issues

Animations: Animations (animated()) only trigger on item addition/removal, not updates (e.g., price changes).
Accessibility: Limited support for screen readers (e.g., no content descriptions for icons in KListItem).
Type Casting: KListSection uses Any for items, requiring type casting.
ViewModel Dependency: KListDemoViewModel is optional and not included in the core library.

Future Improvements

Add custom animation types (e.g., slide-in, scale).
Enhance accessibility with semantics and content descriptions.
Use generics in KListSection for stronger type safety.
Add unit tests for core DSL functionality.

Contributing
Contributions are welcome! Submit pull requests or issues to the repository: https://github.com/prashant/klist-dsl.
License
MIT License - see LICENSE file for details.
