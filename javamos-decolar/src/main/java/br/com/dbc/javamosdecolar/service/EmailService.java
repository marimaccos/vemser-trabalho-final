package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.model.Usuario;
import br.com.dbc.javamosdecolar.model.Venda;
import freemarker.template.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final Configuration fmConfiguration;
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(String template, String email) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Javamos Decolar");
            mimeMessageHelper.setText(template, true);

//            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro ao enviar e-mail.");
        }
    }

    public void sendEmail(Venda venda, String acao, Comprador comprador) throws RegraDeNegocioException {
        this.sendEmail(this.getVendaTemplate(venda, acao), comprador.getLogin());
    }

    public void sendEmail(Usuario usuario) throws RegraDeNegocioException {
        this.sendEmail(this.getNovoUsuarioTemplate(usuario), usuario.getLogin());
    }

    public String getVendaTemplate(Venda venda, String acao) throws RegraDeNegocioException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", venda.getComprador().getNome());
        dados.put("codigo", venda.getCodigo());
        dados.put("email", from);

        Template template = null;

        try {
            switch (acao) {
                case "CRIAR":
                    template = fmConfiguration.getTemplate("venda-realizada-template.ftl");
                    break;
                case "DELETAR":
                    template = fmConfiguration.getTemplate("venda-cancelada-template.ftl");
                    break;
            }
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

        } catch (TemplateException | RuntimeException | IOException e) {
            throw new RegraDeNegocioException("Erro ao enviar e-mail.");
        }
    }
    public String getNovoUsuarioTemplate(Usuario usuario) throws RegraDeNegocioException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuario.getNome());
        dados.put("email", from);

        try {
            Template template = fmConfiguration.getTemplate("usuario-criado-template.ftl");

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

        } catch (TemplateException | RuntimeException | IOException e) {
            throw new RegraDeNegocioException("Erro ao enviar e-mail.");
        }
    }
}
