package com.reserva.controllers;


import com.reserva.models.Convidado;
import com.reserva.models.Evento;
import com.reserva.repository.ConvidadoRepository;
import com.reserva.repository.EventoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository er;

    @Autowired
    private ConvidadoRepository cr;
    @Autowired
    private ConvidadoRepository convidadoRepository;

    @RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
    public String form()
    {
        return "evento/formEvento";
    }

    //Toda a logica e verificação de cadastro de eventos fica aqui.
    @RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
    public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes){
        try {
            if(evento.getHorario().isAfter(evento.getHorarioFinal())){
                    attributes.addFlashAttribute("mensagem", "Horario incompativel, por favor alterar!");
                    evento.setHorarioInvalido(true);
                    return "redirect:/cadastrarEvento";
            }
            if (evento.getHorario().equals(evento.getHorarioFinal())) {
                attributes.addFlashAttribute("mensagem", "Horario incompativel, por favor alterar!");
                evento.setHorarioInvalido(true);
                return "redirect:/cadastrarEvento";
            }
            if (result.hasErrors()) {
                attributes.addFlashAttribute("mensagem", "Verifique os campos!");
                return "redirect:/cadastrarEvento";
            }
            er.save(evento);
            attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
            return "redirect:/cadastrarEvento";

        }catch (Exception e){
            attributes.addFlashAttribute("mensagem", "Os campos não podem estar nulos");
            return "redirect:/cadastrarEvento";
        }
    }

    //Retornando inicialmente lista de eventos
    @RequestMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("/listaEventos.html");
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }

    //Retorno dos detalhes do evento, quando clicado em cima do evento
    @RequestMapping(value= "/{codigo}", method=RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = er.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);

        Iterable<Convidado> convidados = cr.findByEvento(evento);
        mv.addObject("convidados", convidados);

        return mv;
    }

    //Deletar eventos....
    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo) {
        Evento evento = er.findByCodigo(codigo);
        er.delete(evento);
        return "redirect:/eventos";
    }

    //Aqui fica a parte de adicionar convidados a um evento, toda logica e verificação ficam aqui.
    @PostMapping("/{codigo}")
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes){
        try {
            // Verifica se o RG já existe
            Convidado existente = convidadoRepository.findByRg(convidado.getRg());
            if (existente != null) {
                attributes.addFlashAttribute("mensagem", "RG já cadastrado! Digite um novo RG");
                return "redirect:/{codigo}";
            }
            if(result.hasErrors()){
                attributes.addFlashAttribute("mensagem", "Verifique os campos!");
                return "redirect:/{codigo}";
            }

            Evento evento = er.findByCodigo(codigo);
            convidado.setEvento(evento);
            cr.save(convidado);
            attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
            return "redirect:/{codigo}";
        }
        catch (Exception e) {
                attributes.addFlashAttribute("mensagem", "Verifique!");
                return "redirect:/{codigo}";
            }
    }

    //Bom... aqui vc deleta o convidado
    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg) {
        Convidado convidado = cr.findByRg(rg);
        cr.delete(convidado);

        Evento evento = convidado.getEvento();
        long codigoLong = evento.getCodigo();
        String codigo = "" + codigoLong;
        return "redirect:/" + codigo;
    }
}
