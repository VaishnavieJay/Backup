# Getting Started

1. The application starts in port 8080.

2. The contents of the wallet has been set in file WalletStore. To change the value, modify the following lines in file
   ApplicationConfig.java public void loadWallet() { walletStore.setCurrencyNotesList(List.of(1, 50, 5, 5, 10, 1, 20,
   20, 20, 5, 1, 50)); }

3. Test case for the controller - CurrencyDenominationControllerTest.java

4. Test case for the CurrencyDenominationService - CurrencyDenominationServiceTest.java

5. To test the application from the browser, use the following URL
   http://localhost:8080/currency/denomination/183
   where, 183 -> is the amount for which the denomination from the wallet will be displayed



