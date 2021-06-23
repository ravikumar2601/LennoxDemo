Feature: Parts And Supplies

  @Test
  Scenario: Compressors
    Given Login to lennoxPros
    Then User able to view home page
    When Navigate via Menu to Parts And Supplies to Compressors
      | PrimaryCatalog | PartAndSuppliesCatalog |
    Then Validate user at the compressor page
      | PartAndSuppliesCatalog | PartAndSuppliesCatalog |
    When Select Air Compressors
      | PartAndSuppliesCatalog |
    Then Validate the landing page description
      | PartAndSuppliesCatalog | PartAndSuppliesCatalog |
    When Locate for the product 10T46 on the page, if not found navigate to the subsequent pages until the product is found. If the product is not listed in any of the pages then provide the details in the report.
      | CompressorsCatalogNo |
    Then Collect all the listed details pertaining to the product and store
      | ProductListItem |
    When Click on the Product and it will lead to Product detail page.
      | ProductListItem |
    Then Collect all the product details which are highlighted and compare it with the details from the previous page.
      | Product detail |