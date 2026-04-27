const ButtonBase = ({onClick, texto }) => {
    return (
        <button onClick = {onClick} className="button-base">
            {texto}
                   </button>
                   
    );
};
export default ButtonBase;