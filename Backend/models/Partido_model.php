<?php
class Partido_model extends CI_Model {
    public function __construct(){
        parent::__construct();
    }

    public function getPartidos(){
        $query = $this->db->get('Partido');
        return $query->result();
    }

    public function getPartido($idPartido,$cancha,$hora,$fecha){
        $data = array();
        if(!empty($cancha)){
            $cancha = urldecode($cancha);
            $data['cancha'] = $cancha;
        }
        if(!empty($fecha)){
            $fecha = urldecode($fecha);
            $data['fecha'] = $fecha;
        }
        if(!empty($hora)){
            $hora = urldecode($hora);
            $data['hora'] = $hora;
        }
        if (!empty($data) && empty($idPartido)){
            $this->db->set($data);
            $this->db->insert('Partido');
            $idPartido = $this->db->insert_id();
        }
        else if(!empty($data)){
            $this->db->set($data);
            $this->db->where('idPartido',$idPartido);
            $this->db->update('Partido', $data);
        }
        $this->db->select('*');
        $this->db->where('idPartido',$idPartido);
        $this->db->from('Partido');
        $result = $this->db->get()->result_array();
        return $result;
    }

    public function getParticipantes($idPartido,$idEquipo1,$idEquipo2,$nickname){
        $data = array();
        if(!empty($nickname)){
            $nickname = urldecode($nickname);
            $data['nickname'] = $nickname;
        }
        // if(!empty($idEquipo1)){
        //     $idEquipo1 = urldecode($idEquipo1);
        //     $data['idEquipo1'] = $idEquipo1;
        // }
        // if(!empty($idEquipo2)){
        //     $idEquipo2 = urldecode($idEquipo2);
        //     $data['idEquipo2'] = $idEquipo2;
        // }
        if (!empty($data)){
            $this->db->select('*');
            $this->db->where('idPartido',$idPartido);
            $this->db->where('nickname',$nickname);
            $this->db->from('Participantes');
            $result = $this->db->get()->result_array();
            if(empty($result)){
                $idPartido = urldecode($idPartido);
                $data['idPartido'] = $idPartido;
                $this->db->set($data);
                $this->db->insert('Participantes');
            }
        }
//        $this->db->select('idEquipo1');
//        $this->db->select('idEquipo2');
        $this->db->select('nickname');
        $this->db->from('Participantes');
        $this->db->where('idPartido',$idPartido);
        $query = $this->db->get();
        return $query->result();
    }
}
?>
