public class ActiveForeignExchange extends MyRunnable{
    CoinJPanel  coinJPanel;

    public ActiveForeignExchange(CoinJPanel coinJPanel) {
        super(coinJPanel);
    }

    @Override
    public void _run() {
        coinJPanel.runConversion(String.valueOf(coinJPanel.foreignExchange.rateConversion()));
        coinJPanel.run();
    }
}
