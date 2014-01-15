// vari�veis para o efeito janela modal usando jquery

// como o wrap j� tem id definido, ent�o definimos tamb�m um class para que o mesma fun��o sirva
// tanto para o login quanto para as demais p�ginas
var window_focus_id = "#modal_window"; // container que cont�m a tela que receber� o foco
var background_container = "#mask"; // container que receber� a cor escura com determinada transpar�ncia
var modal_window_speed = "fast"; // velocidade para o efeito de janela modal acontecer
var background_container_click_focus = "login"; // campo que receber� o login caso o container de fundo seja clicado.

// fun��o que define o efeito janela modal usando jquery
// Ex.: http://www.maujor.com/blog/2009/04/16/janela-modal-com-jquery/
$(document).ready(function() {

	var id = window_focus_id;
	
	var maskHeight = $(document).height();
	var maskWidth = $(window).width();
	var bgContainer = background_container;

	$(bgContainer).css({
		'width' : maskWidth,
		'height' : maskHeight,
		'position' : 'absolute'
	});
	
	var speed = modal_window_speed;

	$(bgContainer).fadeIn(20);
	$(bgContainer).fadeTo(speed, 0.8);

	var winH = $(window).height();
	var winW = $(window).width();

	$(id).css('top', winH / 2 - $(id).height() / 2);
	$(id).css('left', winW / 2 - $(id).width() / 2);

	$(id).fadeIn(1000);
	
	$(bgContainer).click(function () {
		
		document.getElementById(background_container_click_focus).focus();
	});
});