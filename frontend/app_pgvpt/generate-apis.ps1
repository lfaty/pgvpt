$apiDir = "..\..\contrats_api"
$outDir = "src\app\core\api"

$files = Get-ChildItem -Path $apiDir -Filter "*.yml"

foreach ($file in $files) {
    $apiName = $file.BaseName.Replace("-api", "")
    Write-Host "Generating models for $apiName..."
    npx -y @openapitools/openapi-generator-cli generate -i $file.FullName -g typescript-angular -o "$outDir\$apiName" --additional-properties=ngVersion=17,fileNaming=kebab-case
}
Write-Host "API Generation complete."
