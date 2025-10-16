package telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Pessoa;
import main.Repositorio;

public class TelaCadastro extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JTextField textFieldEmail;
	private JTextField textFieldId;
	private Repositorio rep;
	private boolean salvo = false;
	private Pessoa pessoa;
	private String modo;

	public TelaCadastro(Frame parent, Repositorio rep, Pessoa pessoa, String modo) {
		super(parent, true);
		this.rep = rep;
		this.pessoa = pessoa;
		this.modo = modo;

		inicializaComponentes();
		preencherCamposSeNecessario();
		
		addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	        public void windowOpened(java.awt.event.WindowEvent e) {
	            textFieldNome.requestFocusInWindow();
	        }
	    });
	}

	private void inicializaComponentes() {
		setTitle("Tela de cadastro");
		setBounds(100, 100, 542, 317);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Arial", Font.PLAIN, 14));
		lblId.setBounds(20, 13, 56, 13);
		contentPanel.add(lblId);

		textFieldId = new JTextField();
		textFieldId.setFont(new Font("Arial", Font.PLAIN, 14));
		textFieldId.setEditable(false);
		textFieldId.setToolTipText("Seu ID Gerado");
		textFieldId.setBounds(20, 36, 381, 28);
		contentPanel.add(textFieldId);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNome.setBounds(20, 77, 56, 13);
		contentPanel.add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Arial", Font.PLAIN, 14));
		textFieldNome.setToolTipText("Digite seu nome completo");
		textFieldNome.setBounds(20, 100, 381, 28);
		contentPanel.add(textFieldNome);
		textFieldNome.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		lblEmail.setBounds(20, 146, 56, 13);
		contentPanel.add(lblEmail);

		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		textFieldEmail.setToolTipText("Digite seu email");
		textFieldEmail.setBounds(20, 169, 381, 28);
		contentPanel.add(textFieldEmail);
		textFieldEmail.setColumns(10);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setMnemonic('s');
		btnSalvar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarPessoa();
			}
		});
		buttonPane.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCancelar.setMnemonic('c');
		btnCancelar.addActionListener(e -> fecharJanela());
		buttonPane.add(btnCancelar);
		
		if(modo.equalsIgnoreCase("consultar")) {
			btnSalvar.setVisible(false);
		}
	}

	private void preencherCamposSeNecessario() {
		if (modo.equalsIgnoreCase("incluir")) {
			textFieldId.setText(String.valueOf(rep.getId()));
		}
		else if (modo.equalsIgnoreCase("alterar") && pessoa != null) {
			textFieldId.setText(String.valueOf(pessoa.getIdPessoa()));
			textFieldNome.setText(pessoa.getNomePessoa());
			textFieldEmail.setText(pessoa.getEmailPessoa());
		}
		else if(modo.equalsIgnoreCase("consultar")) {
			
			textFieldId.setEditable(false);
			textFieldId.setText(String.valueOf(pessoa.getIdPessoa()));
			textFieldNome.setEditable(false);
			textFieldNome.setText(pessoa.getNomePessoa());
			textFieldEmail.setEditable(false);
			textFieldEmail.setText(pessoa.getEmailPessoa());
		}
	}

	private void salvarPessoa() {
		String nome = textFieldNome.getText().trim();
		String email = textFieldEmail.getText().trim();

		if (nome.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Preencha o campo de nome corretamente", "ERRO", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Preencha o campo Email corretamente", "ERRO", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (modo.equalsIgnoreCase("incluir")) {
			Pessoa novaPessoa = new Pessoa();
			novaPessoa.setNomePessoa(nome);
			novaPessoa.setEmailPessoa(email);
			rep.adicionarPessoa(novaPessoa);
		} else if (modo.equalsIgnoreCase("alterar") && pessoa != null) {
			pessoa.setNomePessoa(nome);
			pessoa.setEmailPessoa(email);
		}

		this.salvo = true;
		fecharJanela();
	}

	private void fecharJanela() {
		dispose();
	}

	public boolean isSalvo() {
		return salvo;
	}
}
