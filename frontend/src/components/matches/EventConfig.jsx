import {
  FaFutbol,
  FaSquare,
  FaExchangeAlt,
  FaFlag,
  FaFirstAid,
  FaHandPaper
} from "react-icons/fa";

export const EVENT_CONFIG = {
  GOAL: {
    icon: <FaFutbol />,
    color: "#2ecc71",
    label: "Tor"
  },
  YELLOW: {
    icon: <FaSquare />,
    color: "#f1c40f",
    label: "Gelbe Karte"
  },
  RED: {
    icon: <FaSquare />,
    color: "#e74c3c",
    label: "Rote Karte"
  },
  YELLOWRED: {
    icon: <FaSquare />,
    color: "#e67e22",
    label: "Gelb-Rot"
  },
  SUBSTITUTION: {
    icon: <FaExchangeAlt />,
    color: "#3498db",
    label: "Auswechslung"
  },
  OFFSIDE: {
    icon: <FaFlag />,
    color: "#9b59b6",
    label: "Abseits"
  },
  INJURY: {
    icon: <FaFirstAid />,
    color: "#c0392b",
    label: "Verletzung"
  },
  PENALTY: {
    icon: <FaHandPaper />,
    color: "#16a085",
    label: "Elfmeter"
  },
  SAVED_SHOT: {
    icon: <FaHandPaper />,
    color: "#95a5a6",
    label: "Schuss gehalten"
  },
  OTHER: {
    icon: null,
    color: "#7f8c8d",
    label: "Ereignis"
  }
};