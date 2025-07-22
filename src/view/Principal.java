package view;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Principal {
    public static void main(String[] args) {
        // Adicione esta linha para verificar o diretório de trabalho
        System.out.println("Diretório de trabalho: " + System.getProperty("user.dir"));
        
        // Configura o look and feel para parecer com o sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Inicia a aplicação na thread de eventos do Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EnderecoView().setVisible(true);
            }
        });
    }
}
