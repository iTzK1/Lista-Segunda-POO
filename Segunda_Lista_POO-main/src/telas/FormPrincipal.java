package telas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.Pessoa;
import main.Repositorio;

import javax.swing.JButton;
import java.awt.Font;

public class FormPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Repositorio rep;
    private JTable tabela;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FormPrincipal frame = new FormPrincipal();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FormPrincipal() {
    	setResizable(false);
        rep = new Repositorio();
        inicializaComponentes();
        inicializaTabela();
    }

    public void inicializaComponentes() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 599, 448);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnIncluir = new JButton("Incluir");
        btnIncluir.addActionListener(e -> {
            TelaCadastro tela = new TelaCadastro(FormPrincipal.this, rep, null, "incluir");
            tela.setVisible(true);
            if (tela.isSalvo()) {
                atualizaTabela();
            }
        });
        btnIncluir.setFont(new Font("Arial", Font.PLAIN, 14));
        btnIncluir.setMnemonic('i');
        btnIncluir.setBounds(20, 351, 98, 33);
        contentPane.add(btnIncluir);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(FormPrincipal.this, "Selecione uma pessoa para alterar.", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Pessoa pessoaSelecionada = rep.getPessoas().get(linhaSelecionada);
            TelaCadastro tela = new TelaCadastro(FormPrincipal.this, rep, pessoaSelecionada, "alterar");
            tela.setVisible(true);
            if (tela.isSalvo()) {
                atualizaTabela();
            }
        });
        btnEditar.setMnemonic('e');
        btnEditar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnEditar.setBounds(128, 351, 98, 33);
        contentPane.add(btnEditar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(FormPrincipal.this, "Selecione uma pessoa para excluir.", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(FormPrincipal.this,
                    "Deseja realmente excluir esta pessoa?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Pessoa pessoaSelecionada = rep.getPessoas().get(linhaSelecionada);
                rep.removerPessoa(pessoaSelecionada);
                atualizaTabela();
            }
        });
        btnExcluir.setMnemonic('x');
        btnExcluir.setFont(new Font("Arial", Font.PLAIN, 14));
        btnExcluir.setBounds(236, 351, 98, 33);
        contentPane.add(btnExcluir);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> System.exit(0));
        btnFechar.setMnemonic('f');
        btnFechar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnFechar.setBounds(460, 351, 98, 33);
        contentPane.add(btnFechar);
    }

    	public void inicializaTabela() {
        DefaultTableModel model = new DefaultTableModel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Email");

        tabela = new JTable(model);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.setRowHeight(22);

        tabela.setDefaultEditor(Object.class, null);

        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 10, 555, 331);
        contentPane.add(scroll);

        JButton btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(FormPrincipal.this, "Selecione uma pessoa para consultar.", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Pessoa pessoaSelecionada = rep.getPessoas().get(linhaSelecionada);
            TelaCadastro tela = new TelaCadastro(FormPrincipal.this, rep, pessoaSelecionada, "consultar");
            tela.setVisible(true);
        });
        btnConsultar.setMnemonic('c');
        btnConsultar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnConsultar.setBounds(344, 351, 98, 33);
        contentPane.add(btnConsultar);
    }


    private void atualizaTabela() {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setRowCount(0);

        for (Pessoa p : rep.getPessoas()) {
            Object[] linha = {
                p.getIdPessoa(),
                p.getNomePessoa(),
                p.getEmailPessoa(),
            };
            model.addRow(linha);
        }
    }
}
